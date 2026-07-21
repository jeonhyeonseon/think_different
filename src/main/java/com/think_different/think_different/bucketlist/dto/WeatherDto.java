package com.think_different.think_different.bucketlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDto {

    private Double temperature;
    private String main;
    private String description;

    public boolean isUnknown() {
        return "UNKNOWN".equals(main);
    }

    public static WeatherDto unknown() {
        return WeatherDto.builder()
                .temperature(null)
                .main("UNKNOWN")
                .description("날씨 정보를 가져올 수 없어요")
                .build();
    }
}
