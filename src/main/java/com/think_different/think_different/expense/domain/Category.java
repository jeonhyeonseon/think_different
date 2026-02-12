package com.think_different.think_different.expense.domain;

import lombok.Getter;

@Getter
public enum Category {

    FOOD("FOOD", "음식"), // 음식
    CAFE("CAFE", "카페"), // 카페
    TRAFFIC("TRAFFIC", "교통"), // 교통
    TELECOM("TELECOM", "통신"), // 통신
    HOSPITAL("HASPITAL", "병원"), // 병원
    OTT("OTT", "OTT"),
    INSURANCE("INSURANCE", "보험"), // 보험
    SAVINGS("SAVINGS", "적금"), // 적금
    INVEST("INVEST", "투자"), // 투자
    ETC("ETC", "기타");

    private String categoryName;
    private String displayCategoryName;

    Category(String categoryName, String displayCategoryName) {
        this.categoryName = categoryName;
        this.displayCategoryName = displayCategoryName;
    }
}
