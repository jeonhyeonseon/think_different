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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class WeatherService {

    private static final DateTimeFormatter CACHE_KEY_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH");
    private static final int CACHE_BUCKET_MINUTES = 30;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.city}")
    private String city;

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

        return city + "-" + now.format(CACHE_KEY_FORMATTER) + "-" + bucket;
    }

    private WeatherDto requestWeatherApi() {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                    .queryParam("q", city)
                    .queryParam("appid", apiKey)
                    .queryParam("units", "metric")
                    .queryParam("lang", "kr")
                    .encode()
                    .build()
                    .toUriString();

            String response = readUrl(url);

            JsonNode root = objectMapper.readTree(response);
            JsonNode weatherNode = root.path("weather").get(0);

            return WeatherDto.builder()
                    .temperature(root.path("main").path("temp").asDouble())
                    .main(weatherNode.path("main").asText())
                    .description(weatherNode.path("description").asText())
                    .build();

        } catch (Exception e) {
            log.warn("날씨 조회에 실패했습니다.", e);
            return WeatherDto.unknown();
        }
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
