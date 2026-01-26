package com.think_different.think_different.expense.dto;

import com.think_different.think_different.expense.domain.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseListResponseDto {

    private List<ExpenseResponseDto> expenseList;
    private Long totalAmount;
    private YearMonth yearMonth;

    public static ExpenseListResponseDto fromExpense(List<Expense> expenseList, long totalAmount, YearMonth yearMonth) {

        List<ExpenseResponseDto> responseDtoList = expenseList.stream()
                                                    .map(ExpenseResponseDto::fromExpense).toList();

        return new ExpenseListResponseDto(
                responseDtoList,
                totalAmount,
                yearMonth
        );
    }
}
