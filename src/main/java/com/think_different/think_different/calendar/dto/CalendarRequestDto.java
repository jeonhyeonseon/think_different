package com.think_different.think_different.calendar.dto;

import com.think_different.think_different.calendar.entity.Calendar;
import com.think_different.think_different.calendar.entity.CalendarCategory;
import com.think_different.think_different.couple.domain.Couple;
import com.think_different.think_different.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class CalendarRequestDto {

    @NotBlank(message = "일정을 입력해주세요.")
    @Size(max = 20, message = "일정은 20자 미만으로 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[가-힣a-zA-Z])[가-힣a-zA-Z0-9\\s]+$",
            message = "일정은 한글 또는 영어를 포함해야 하며, 숫자만 입력할 수 없습니다."
    )
    private String title;
    private CalendarCategory category;
    @Size(max = 300, message = "메모는 300자 이하로 입력해주세요.")
    private String memo;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public Calendar toCalendar(Couple couple, Member createdBy) {
        return Calendar.builder()
                .couple(couple)
                .createdBy(createdBy)
                .title(title)
                .category(category == null ? CalendarCategory.ETC : category)
                .memo(memo)
                .scheduleDate(scheduleDate)
                .startTime(startTime)
                .endTime(endTime)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
