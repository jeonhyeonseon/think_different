package com.think_different.think_different.expense.dto;

import com.think_different.think_different.expense.domain.Category;
import com.think_different.think_different.expense.domain.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseUpdateRequestDto {

    private String detail;
    private Category category;
    private String account;
    private Long amount;
    private LocalDate paymentDate;
    private String memo;

    public static ExpenseUpdateRequestDto fromExpense(Expense expense) {
        return new ExpenseUpdateRequestDto(
                expense.getDetail(),
                expense.getCategory(),
                expense.getAccount(),
                expense.getAmount(),
                expense.getPaymentDate(),
                expense.getMemo()
        );
    }
}
