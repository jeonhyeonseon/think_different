package com.think_different.think_different.bucketlist.service;

import com.think_different.think_different.bucketlist.dto.BucketListRecommendationResponseDto;
import com.think_different.think_different.bucketlist.dto.BucketListResponseDto;
import com.think_different.think_different.bucketlist.dto.WeatherDto;
import com.think_different.think_different.bucketlist.entity.BucketList;
import com.think_different.think_different.bucketlist.entity.BucketListPlaceType;
import com.think_different.think_different.bucketlist.entity.BucketListPriority;
import com.think_different.think_different.bucketlist.entity.BucketListSeason;
import com.think_different.think_different.bucketlist.repository.BucketListRepository;
import com.think_different.think_different.couple.domain.Couple;
import com.think_different.think_different.couple.domain.CoupleMember;
import com.think_different.think_different.couple.repository.CoupleMemberRepository;
import com.think_different.think_different.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * 버킷리스트 추천 로직 전용 서비스.
 * 날씨(WeatherService) + 계절 + 평일/주말 조건으로 커플의 미완료 버킷리스트 항목을 필터링/정렬한다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BucketListRecommendationService {

    private static final int RECOMMENDATION_LIMIT = 5;
    private static final double COLD_THRESHOLD = 0.0;
    private static final double HOT_THRESHOLD = 33.0;
    private static final Set<String> BAD_OUTDOOR_WEATHER = Set.of("RAIN", "DRIZZLE", "THUNDERSTORM", "SNOW");

    private final BucketListRepository bucketListRepository;
    private final CoupleMemberRepository coupleMemberRepository;
    private final WeatherService weatherService;

    public BucketListRecommendationResponseDto recommend(Member member) {

        Couple couple = coupleMemberRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("커플 정보를 찾을 수 없습니다."))
                .getCouple();

        LocalDate today = LocalDate.now();
        BucketListSeason currentSeason = BucketListSeason.fromMonth(today.getMonthValue());
        boolean weekend = today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY;

        WeatherDto weather = weatherService.getCurrentWeather();
        boolean outdoorFriendly = isOutdoorFriendly(weather);

        List<BucketListResponseDto> recommendations = bucketListRepository.findByCoupleAndCompletedFalse(couple)
                .stream()
                .filter(bucketList -> matchesSeason(bucketList, currentSeason))
                .filter(bucketList -> matchesWeather(bucketList, outdoorFriendly))
                .sorted(recommendationOrder(weekend))
                .limit(RECOMMENDATION_LIMIT)
                .map(BucketListResponseDto::fromBucketList)
                .toList();

        return BucketListRecommendationResponseDto.builder()
                .recommendations(recommendations)
                .season(currentSeason)
                .weekend(weekend)
                .weather(weather)
                .build();
    }

    private boolean isOutdoorFriendly(WeatherDto weather) {
        if (weather.isUnknown()) {
            return true;
        }

        if (BAD_OUTDOOR_WEATHER.contains(weather.getMain().toUpperCase())) {
            return false;
        }

        Double temperature = weather.getTemperature();

        return temperature == null || (temperature >= COLD_THRESHOLD && temperature <= HOT_THRESHOLD);
    }

    private boolean matchesSeason(BucketList bucketList, BucketListSeason currentSeason) {
        return bucketList.getSeason() == currentSeason || bucketList.getSeason() == BucketListSeason.ALL_SEASON;
    }

    private boolean matchesWeather(BucketList bucketList, boolean outdoorFriendly) {
        return bucketList.getPlaceType() == BucketListPlaceType.INDOOR || outdoorFriendly;
    }

    private Comparator<BucketList> recommendationOrder(boolean weekend) {
        return Comparator
                .comparingInt((BucketList bucketList) -> priorityWeight(bucketList.getPriority()))
                .thenComparingInt(bucketList -> placeTypeWeight(bucketList.getPlaceType(), weekend));
    }

    private int priorityWeight(BucketListPriority priority) {
        return switch (priority) {
            case MUST_DO -> 0;
            case SOMEDAY -> 1;
            case HOLD -> 2;
        };
    }

    private int placeTypeWeight(BucketListPlaceType placeType, boolean weekend) {
        boolean outdoorFirst = weekend;
        boolean isOutdoor = placeType == BucketListPlaceType.OUTDOOR;

        return (isOutdoor == outdoorFirst) ? 0 : 1;
    }
}
