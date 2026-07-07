package com.think_different.think_different.calendar.service;

import com.think_different.think_different.calendar.dto.AnniversaryDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnniversaryService {

    public List<AnniversaryDto> getMonthlyAnniversaries(LocalDate startDate, int year, int month) {
        if (startDate == null) {
            return List.of();
        }

        YearMonth targetMonth = YearMonth.of(year, month);
        LocalDate monthStart = targetMonth.atDay(1);
        LocalDate monthEnd = targetMonth.atEndOfMonth();

        List<AnniversaryDto> anniversaries = new ArrayList<>();

        // 100일 단위: 100일, 200일, 300일 ... 3000일까지
        for (int day = 100; day <= 99999999; day += 100) {
            LocalDate anniversaryDate = startDate.plusDays(day - 1);

            if (!anniversaryDate.isBefore(monthStart) && !anniversaryDate.isAfter(monthEnd)) {
                anniversaries.add(toAnniversaryEvent(day + "일", anniversaryDate));
            }
        }

        // 1주년, 2주년, 3주년 ...
        for (int yearCount = 1; yearCount <= 20; yearCount++) {
            LocalDate anniversaryDate = startDate.plusYears(yearCount);

            if (!anniversaryDate.isBefore(monthStart) && !anniversaryDate.isAfter(monthEnd)) {
                anniversaries.add(toAnniversaryEvent(yearCount + "주년", anniversaryDate));
            }
        }

        return anniversaries;
    }

    private AnniversaryDto toAnniversaryEvent(String title, LocalDate date) {
        return AnniversaryDto.builder()
                .title(title)
                .start(date.toString())
                .backgroundColor("#FF7B7B")
                .borderColor("#FF7B7B")
                .textColor("#fff")
                .classNames(List.of("anniversary-event"))
                .build();
    }
}
