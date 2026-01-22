package com.think_different.think_different.expense.dto;

import com.think_different.think_different.expense.domain.Category;
import com.think_different.think_different.expense.domain.Expense;
import com.think_different.think_different.expense.domain.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCreateRequestDto {

    private String detail;
    private ExpenseType expenseType;
    private Category category;
    private String account;
    private Long amount;
    private LocalDate paymentDate;
    private String memo;

    public Expense toExpense() {
        return Expense.builder()
                .detail(this.detail)
                .expenseType(this.expenseType)
                .category(this.category)
                .account(this.account)
                .amount(this.amount)
                .paymentDate(LocalDate.now())
                .memo(this.memo)
                .build();
    }
}
