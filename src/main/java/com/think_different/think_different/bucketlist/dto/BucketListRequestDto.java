package com.think_different.think_different.bucketlist.dto;

import com.think_different.think_different.bucketlist.entity.BucketList;
import com.think_different.think_different.bucketlist.entity.BucketListPlaceType;
import com.think_different.think_different.bucketlist.entity.BucketListPriority;
import com.think_different.think_different.bucketlist.entity.BucketListSeason;
import com.think_different.think_different.couple.domain.Couple;
import com.think_different.think_different.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BucketListRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 50, message = "제목은 50자 이하로 입력해주세요.")
    private String title;

    @Size(max = 300, message = "메모는 300자 이하로 입력해주세요.")
    private String memo;

    private BucketListPlaceType placeType;

    private BucketListSeason season;

    private BucketListPriority priority;

    public BucketList toBucketList(Couple couple, Member suggestedBy) {
        return BucketList.builder()
                .couple(couple)
                .suggestedBy(suggestedBy)
                .title(title)
                .memo(memo)
                .placeType(placeType == null ? BucketListPlaceType.INDOOR : placeType)
                .season(season == null ? BucketListSeason.ALL_SEASON : season)
                .priority(priority == null ? BucketListPriority.SOMEDAY : priority)
                .completed(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
