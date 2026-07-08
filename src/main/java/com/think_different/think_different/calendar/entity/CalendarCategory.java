package com.think_different.think_different.calendar.entity;

import lombok.Getter;

@Getter
public enum CalendarCategory {

    DATE("데이트", "#FF8A80"),
    BIRTHDAY("생일", "#C4B5FD"),
    FRIEND("친구", "#4ECDC4"),
    DINNER("회식", "#F59E0B"),
    EXAM("시험", "#74C0FC"),
    INTERVIEW("면접", "#A47551"),
    TRAVEL("여행", "#2EC4B6"),
    ETC("기타", "#9CA3AF");

    private final String displayName;
    private final String color;

    CalendarCategory(String displayName, String color) {
        this.displayName = displayName;
        this.color = color;
    }
}
