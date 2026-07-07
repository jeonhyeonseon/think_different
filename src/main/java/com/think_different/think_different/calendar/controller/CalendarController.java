package com.think_different.think_different.calendar.controller;

import com.think_different.think_different.calendar.dto.AnniversaryDto;
import com.think_different.think_different.calendar.dto.CalendarRequestDto;
import com.think_different.think_different.calendar.dto.CalendarResponseDto;
import com.think_different.think_different.calendar.dto.HolidayDto;
import com.think_different.think_different.calendar.service.AnniversaryService;
import com.think_different.think_different.calendar.service.CalendarService;
import com.think_different.think_different.calendar.service.HolidayService;
import com.think_different.think_different.config.webSecurity.CustomUserDetails;
import com.think_different.think_different.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final HolidayService holidayService;
    private final AnniversaryService anniversaryService;

    @GetMapping("/events")
    @ResponseBody
    public List<Object> getCalendarEvents(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                          @RequestParam int year,
                                          @RequestParam int month) {

        Member member = customUserDetails.getMember();
        YearMonth yearMonth = YearMonth.of(year, month);

        List<CalendarResponseDto> schedules = calendarService.getMonthlySchedules(member, yearMonth);

        List<HolidayDto> holidays = holidayService.getHolidayEvents(year, month);

        LocalDate startDate = calendarService.getCoupleStartDate(member);

        List<AnniversaryDto> anniversaries = anniversaryService.getMonthlyAnniversaries(startDate, year, month);

        List<Object> events = new ArrayList<>();
        events.addAll(schedules);
        events.addAll(holidays);
        events.addAll(anniversaries);

        return events;
    }

    @GetMapping
    public String showCalendar(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                               Model model) {

        Member member = customUserDetails.getMember();

        YearMonth currentMonth = YearMonth.now();

        List<CalendarResponseDto> calendarResponseDto = calendarService.getMonthlySchedules(member, currentMonth);

        List<HolidayDto> holidayDto = holidayService.getHolidayEvents(currentMonth.getYear(), currentMonth.getMonthValue());

        model.addAttribute("member", member);
        model.addAttribute("schedules", calendarResponseDto);
        model.addAttribute("holidays", holidayDto);

        return "calendar/calendar";
    }

    @GetMapping("/daily")
    public List<CalendarResponseDto> showDailySchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                       @RequestParam LocalDate scheduleDate) {

        Member member = customUserDetails.getMember();

        return calendarService.showDailySchedule(member, scheduleDate);
    }

    @PostMapping
    public String createSchedule(@ModelAttribute CalendarRequestDto calendarRequestDto,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Member member = customUserDetails.getMember();

        calendarService.registerSchedule(member, calendarRequestDto);

        return "redirect:/calendar";
    }

    @PostMapping("/{calendarId}/edit")
    public String updateSchedule(@PathVariable Long calendarId,
                                 @ModelAttribute CalendarRequestDto calendarRequestDto,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Member member = customUserDetails.getMember();

        calendarService.updateSchedule(member, calendarId, calendarRequestDto);

        return "redirect:/calendar";
    }

    @PostMapping("/{calendarId}/delete")
    public String deleteShcedule(@PathVariable Long calendarId,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Member member = customUserDetails.getMember();

        calendarService.deleteSchedule(member, calendarId);

        return "redirect:/calendar";
    }
}
