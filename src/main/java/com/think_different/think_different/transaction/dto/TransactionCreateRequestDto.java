package com.think_different.think_different.transaction.dto;

import com.think_different.think_different.transaction.domain.Transaction;
import com.think_different.think_different.transaction.domain.TransactionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreateRequestDto {

    private String detail;
    private TransactionCategory transactionCategory;
    private Long amount;
    private LocalDate transactionDate;

    public Transaction toExpense() {
        return Transaction.builder()
                .detail(this.detail)
                .transactionCategory(this.transactionCategory)
                .amount(this.amount)
                .transactionDate(LocalDate.now())
                .build();
    }
}
