package com.think_different.think_different.bucketlist.controller;

import com.think_different.think_different.bucketlist.dto.BucketListRecommendationResponseDto;
import com.think_different.think_different.bucketlist.dto.BucketListRequestDto;
import com.think_different.think_different.bucketlist.dto.BucketListResponseDto;
import com.think_different.think_different.bucketlist.service.BucketListRecommendationService;
import com.think_different.think_different.bucketlist.service.BucketListService;
import com.think_different.think_different.calendar.dto.CalendarRequestDto;
import com.think_different.think_different.calendar.entity.CalendarCategory;
import com.think_different.think_different.calendar.service.CalendarService;
import com.think_different.think_different.config.webSecurity.CustomUserDetails;
import com.think_different.think_different.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/bucketlist")
@RequiredArgsConstructor
public class BucketListController {

    private final BucketListService bucketListService;
    private final BucketListRecommendationService bucketListRecommendationService;
    private final CalendarService calendarService;

    @GetMapping
    public String showBucketList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                 Model model) {

        Member member = customUserDetails.getMember();

        List<BucketListResponseDto> bucketListItems = bucketListService.getBucketList(member);
        BucketListRecommendationResponseDto recommendation = bucketListRecommendationService.recommend(member);

        model.addAttribute("member", member);
        model.addAttribute("bucketListItems", bucketListItems);
        model.addAttribute("recommendation", recommendation);

        return "bucketlist/bucketlist";
    }

    @PostMapping
    public String createBucketList(@Valid @ModelAttribute BucketListRequestDto requestDto,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Member member = customUserDetails.getMember();

        bucketListService.createBucketList(member, requestDto);

        return "redirect:/bucketlist";
    }

    @PostMapping("/{bucketListId}/edit")
    public String updateBucketList(@PathVariable Long bucketListId,
                                   @Valid @ModelAttribute BucketListRequestDto requestDto,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Member member = customUserDetails.getMember();

        bucketListService.updateBucketList(member, bucketListId, requestDto);

        return "redirect:/bucketlist";
    }

    @PostMapping("/{bucketListId}/delete")
    public String deleteBucketList(@PathVariable Long bucketListId,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Member member = customUserDetails.getMember();

        bucketListService.deleteBucketList(member, bucketListId);

        return "redirect:/bucketlist";
    }

    @PostMapping("/{bucketListId}/complete")
    public String toggleComplete(@PathVariable Long bucketListId,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Member member = customUserDetails.getMember();

        bucketListService.toggleComplete(member, bucketListId);

        return "redirect:/bucketlist";
    }

    @PostMapping("/{bucketListId}/add-to-calendar")
    public String addToCalendar(@PathVariable Long bucketListId,
                                @RequestParam LocalDate scheduleDate,
                                @RequestParam(required = false) LocalTime startTime,
                                @RequestParam(required = false) LocalTime endTime,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Member member = customUserDetails.getMember();

        BucketListResponseDto bucketListItem = bucketListService.getBucketListItem(member, bucketListId);

        CalendarRequestDto calendarRequestDto = new CalendarRequestDto();
        calendarRequestDto.setTitle(bucketListItem.getTitle());
        calendarRequestDto.setMemo(bucketListItem.getMemo());
        calendarRequestDto.setCategory(CalendarCategory.DATE);
        calendarRequestDto.setScheduleDate(scheduleDate);
        calendarRequestDto.setStartTime(startTime);
        calendarRequestDto.setEndTime(endTime);

        calendarService.registerSchedule(member, calendarRequestDto);

        return "redirect:/calendar";
    }
}
