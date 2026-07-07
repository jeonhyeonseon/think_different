package com.think_different.think_different.calendar.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.think_different.think_different.calendar.dto.HolidayDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HolidayService {

    @Value("${holiday.api.key}")
    private String serviceKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<HolidayDto> getHolidayEvents(int year, int month) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo")
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("solYear", year)
                    .queryParam("solMonth", String.format("%02d", month))
                    .queryParam("_type", "json")
                    .encode()
                    .build()
                    .toUriString();

            String response = readUrl(url);
            JsonNode items = objectMapper.readTree(response)
                    .path("response")
                    .path("body")
                    .path("items")
                    .path("item");

            List<HolidayDto> holidays = new ArrayList<>();

            if (items.isArray()) {
                for (JsonNode item : items) {
                    holidays.add(toHolidayEvent(item));
                }
            } else if (!items.isMissingNode() && !items.isEmpty()) {
                holidays.add(toHolidayEvent(items));
            }

            return holidays;

        } catch (Exception e) {
            return List.of();
        }
    }

    private HolidayDto toHolidayEvent(JsonNode item) {
        String name = item.path("dateName").asText();
        String locdate = item.path("locdate").asText();

        LocalDate date = LocalDate.of(
                Integer.parseInt(locdate.substring(0, 4)),
                Integer.parseInt(locdate.substring(4, 6)),
                Integer.parseInt(locdate.substring(6, 8))
        );

        return HolidayDto.builder()
                .title(name)
                .start(date.toString())
                .backgroundColor("#FFB6B6")
                .borderColor("#FFB6B6")
                .textColor("#fff")
                .classNames(List.of("holiday-event"))
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
