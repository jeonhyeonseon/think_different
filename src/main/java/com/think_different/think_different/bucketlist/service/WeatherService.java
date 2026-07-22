package com.think_different.think_different.bucketlist.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.think_different.think_different.bucketlist.dto.WeatherDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class WeatherService {

    private static final String API_URL = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

    // 서울
    private static final int NX = 60;
    private static final int NY = 127;

    // 단기예보 발표시각(매 3시간). API 반영 지연을 고려해 조회 시각에서 일정 시간을 뺀 뒤 계산한다.
    private static final int[] BASE_HOURS = {2, 5, 8, 11, 14, 17, 20, 23};
    private static final int PUBLISH_DELAY_MINUTES = 10;

    // PTY 4(소나기)는 안내된 매핑표에는 없지만 강수 상황이므로 비로 취급한다.
    private static final Set<Integer> RAIN_PTY = Set.of(1, 2, 4, 5, 6);
    private static final Set<Integer> SNOW_PTY = Set.of(3, 7);

    private static final DateTimeFormatter BASE_DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
    private static final DateTimeFormatter FCST_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    private static final DateTimeFormatter CACHE_KEY_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH");
    private static final int CACHE_BUCKET_MINUTES = 30;

    @Value("${weather.api.key}")
    private String apiKey;

    private final Map<String, WeatherDto> weatherCache = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherDto getCurrentWeather() {

        if (apiKey == null || apiKey.isBlank()) {
            return WeatherDto.unknown();
        }

        String cacheKey = buildCacheKey();

        if (weatherCache.containsKey(cacheKey)) {
            return weatherCache.get(cacheKey);
        }

        WeatherDto weather = requestWeatherApi();
        weatherCache.put(cacheKey, weather);

        return weather;
    }

    private String buildCacheKey() {
        LocalDateTime now = LocalDateTime.now();
        int bucket = (now.getMinute() / CACHE_BUCKET_MINUTES) * CACHE_BUCKET_MINUTES;

        return NX + "-" + NY + "-" + now.format(CACHE_KEY_FORMATTER) + "-" + bucket;
    }

    private WeatherDto requestWeatherApi() {
        try {
            String url = buildRequestUrl();
            String response = readUrl(url);

            JsonNode root = objectMapper.readTree(response);
            JsonNode header = root.path("response").path("header");

            if (!"00".equals(header.path("resultCode").asText())) {
                log.warn("기상청 단기예보 조회 실패: {}", header.path("resultMsg").asText());
                return WeatherDto.unknown();
            }

            Map<String, String> forecast = nearestForecast(
                    root.path("response").path("body").path("items").path("item")
            );

            String pty = forecast.get("PTY");
            String sky = forecast.get("SKY");

            if (pty == null || sky == null) {
                return WeatherDto.unknown();
            }

            return toWeatherDto(Integer.parseInt(pty), Integer.parseInt(sky), forecast.get("TMP"));

        } catch (Exception e) {
            log.warn("날씨 조회에 실패했습니다.", e);
            return WeatherDto.unknown();
        }
    }

    private String buildRequestUrl() {
        String[] baseDateTime = resolveBaseDateTime();

        return UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .queryParam("serviceKey", apiKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1000)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDateTime[0])
                .queryParam("base_time", baseDateTime[1])
                .queryParam("nx", NX)
                .queryParam("ny", NY)
                .encode()
                .build()
                .toUriString();
    }

    private String[] resolveBaseDateTime() {
        LocalDateTime adjusted = LocalDateTime.now().minusMinutes(PUBLISH_DELAY_MINUTES);

        LocalDate date = adjusted.toLocalDate();
        int hour = adjusted.getHour();

        int baseHour = -1;
        for (int i = BASE_HOURS.length - 1; i >= 0; i--) {
            if (hour >= BASE_HOURS[i]) {
                baseHour = BASE_HOURS[i];
                break;
            }
        }

        if (baseHour == -1) {
            date = date.minusDays(1);
            baseHour = BASE_HOURS[BASE_HOURS.length - 1];
        }

        return new String[]{date.format(BASE_DATE_FORMATTER), String.format("%02d00", baseHour)};
    }

    private Map<String, String> nearestForecast(JsonNode items) {
        TreeMap<String, Map<String, String>> byFcstDateTime = new TreeMap<>();

        for (JsonNode item : items) {
            String key = item.path("fcstDate").asText() + item.path("fcstTime").asText();
            byFcstDateTime
                    .computeIfAbsent(key, k -> new HashMap<>())
                    .put(item.path("category").asText(), item.path("fcstValue").asText());
        }

        if (byFcstDateTime.isEmpty()) {
            return Map.of();
        }

        String nowKey = LocalDateTime.now().format(FCST_DATETIME_FORMATTER);

        for (Map.Entry<String, Map<String, String>> entry : byFcstDateTime.entrySet()) {
            if (entry.getKey().compareTo(nowKey) >= 0) {
                return entry.getValue();
            }
        }

        return byFcstDateTime.get(byFcstDateTime.lastKey());
    }

    private WeatherDto toWeatherDto(int pty, int sky, String tmpValue) {
        String main;
        String description;

        if (RAIN_PTY.contains(pty)) {
            main = "Rain";
            description = "비";
        } else if (SNOW_PTY.contains(pty)) {
            main = "Snow";
            description = "눈";
        } else if (sky == 1) {
            main = "Clear";
            description = "맑음";
        } else {
            main = "Clouds";
            description = "흐림";
        }

        Double temperature = (tmpValue == null || tmpValue.isBlank()) ? null : Double.parseDouble(tmpValue);

        return WeatherDto.builder()
                .temperature(temperature)
                .main(main)
                .description(description)
                .build();
    }

    private String readUrl(String url) throws Exception {
        StringBuilder result = new StringBuilder();

        URL requestUrl = new URL(url);

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(requestUrl.openStream())
        )) {
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();
    }
}
