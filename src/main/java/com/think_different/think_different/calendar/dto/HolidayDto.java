package com.think_different.think_different.calendar.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@Builder
public class HolidayDto {

    private String title;
    private String start;
    private String backgroundColor;
    private String borderColor;
    private String textColor;
    private List<String> classNames;

}
