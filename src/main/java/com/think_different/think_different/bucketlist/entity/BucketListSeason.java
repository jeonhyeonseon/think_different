package com.think_different.think_different.bucketlist.entity;

import lombok.Getter;

@Getter
public enum BucketListSeason {

    SPRING("봄"),
    SUMMER("여름"),
    FALL("가을"),
    WINTER("겨울"),
    ALL_SEASON("사계절");

    private final String displayName;

    BucketListSeason(String displayName) {
        this.displayName = displayName;
    }

    public static BucketListSeason fromMonth(int month) {
        if (month >= 3 && month <= 5) {
            return SPRING;
        }
        if (month >= 6 && month <= 8) {
            return SUMMER;
        }
        if (month >= 9 && month <= 11) {
            return FALL;
        }
        return WINTER;
    }
}
