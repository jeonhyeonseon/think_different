package com.think_different.think_different.bucketlist.entity;

import com.think_different.think_different.couple.domain.Couple;
import com.think_different.think_different.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_bucket_list")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id", nullable = false)
    private Couple couple;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggested_by", nullable = false)
    private Member suggestedBy;

    @Column(nullable = false)
    private String title;

    @Column(length = 300)
    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BucketListPlaceType placeType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BucketListSeason season;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BucketListPriority priority;

    @Column(nullable = false)
    private boolean completed;

    private LocalDateTime createdAt;

    public void updateInfo(String title, String memo, BucketListPlaceType placeType, BucketListSeason season, BucketListPriority priority) {
        this.title = title;
        this.memo = memo;
        this.placeType = placeType == null ? BucketListPlaceType.INDOOR : placeType;
        this.season = season == null ? BucketListSeason.ALL_SEASON : season;
        this.priority = priority == null ? BucketListPriority.SOMEDAY : priority;
    }

    public void toggleComplete() {
        this.completed = !this.completed;
    }
}
