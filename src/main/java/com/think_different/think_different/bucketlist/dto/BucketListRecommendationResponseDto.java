package com.think_different.think_different.bucketlist.dto;

import com.think_different.think_different.bucketlist.entity.BucketListSeason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketListRecommendationResponseDto {

    private List<BucketListResponseDto> recommendations;
    private BucketListSeason season;
    private boolean weekend;
    private WeatherDto weather;
}
