package com.think_different.think_different.bucketlist.entity;

import lombok.Getter;

@Getter
public enum BucketListPriority {

    MUST_DO("꼭 하고 싶어요"),
    SOMEDAY("언젠가"),
    HOLD("보류");

    private final String displayName;

    BucketListPriority(String displayName) {
        this.displayName = displayName;
    }
}
