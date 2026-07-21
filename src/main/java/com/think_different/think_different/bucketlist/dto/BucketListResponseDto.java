package com.think_different.think_different.bucketlist.dto;

import com.think_different.think_different.bucketlist.entity.BucketList;
import com.think_different.think_different.bucketlist.entity.BucketListPlaceType;
import com.think_different.think_different.bucketlist.entity.BucketListPriority;
import com.think_different.think_different.bucketlist.entity.BucketListSeason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketListResponseDto {

    private Long id;
    private String title;
    private String memo;

    private BucketListPlaceType placeType;
    private String placeTypeDisplayName;

    private BucketListSeason season;
    private String seasonDisplayName;

    private BucketListPriority priority;
    private String priorityDisplayName;

    private boolean completed;

    private Long suggestedByMemberId;
    private String suggestedByName;

    private LocalDateTime createdAt;

    public static BucketListResponseDto fromBucketList(BucketList bucketList) {
        return BucketListResponseDto.builder()
                .id(bucketList.getId())
                .title(bucketList.getTitle())
                .memo(bucketList.getMemo())
                .placeType(bucketList.getPlaceType())
                .placeTypeDisplayName(bucketList.getPlaceType().getDisplayName())
                .season(bucketList.getSeason())
                .seasonDisplayName(bucketList.getSeason().getDisplayName())
                .priority(bucketList.getPriority())
                .priorityDisplayName(bucketList.getPriority().getDisplayName())
                .completed(bucketList.isCompleted())
                .suggestedByMemberId(bucketList.getSuggestedBy().getId())
                .suggestedByName(bucketList.getSuggestedBy().getName())
                .createdAt(bucketList.getCreatedAt())
                .build();
    }
}
