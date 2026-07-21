package com.think_different.think_different.bucketlist.entity;

import lombok.Getter;

@Getter
public enum BucketListPlaceType {

    INDOOR("실내"),
    OUTDOOR("야외");

    private final String displayName;

    BucketListPlaceType(String displayName) {
        this.displayName = displayName;
    }
}
