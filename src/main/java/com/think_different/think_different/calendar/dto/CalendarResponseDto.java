package com.think_different.think_different.calendar.dto;

import com.think_different.think_different.calendar.entity.Calendar;
import com.think_different.think_different.calendar.entity.CalendarCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarResponseDto {

    private Long id;
    private String title;
    private String memo;
    private LocalDate scheduleDate;

    private CalendarCategory category;
    private String categoryDisplayName;
    private String backgroundColor;
    private String borderColor;
    private String textColor;

    private LocalTime startTime;
    private LocalTime endTime;

    public static CalendarResponseDto fromCalendar(Calendar calendar) {
        return CalendarResponseDto.builder()
                .id(calendar.getId())
                .title(calendar.getTitle())
                .memo(calendar.getMemo())
                .scheduleDate(calendar.getScheduleDate())
                .category(calendar.getCategory())
                .categoryDisplayName(calendar.getCategory().getDisplayName())
                .backgroundColor(calendar.getCategory().getColor())
                .borderColor(calendar.getCategory().getColor())
                .textColor("#fff")
                .startTime(calendar.getStartTime())
                .endTime(calendar.getEndTime())
                .build();
    }
}
