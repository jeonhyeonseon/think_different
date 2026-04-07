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
public class TransactionUpdateRequestDto {

    private Long id;
    private String detail;
    private TransactionCategory transactionCategory;
    private Long amount;
    private LocalDate paymentDate;

    public static TransactionUpdateRequestDto fromExpense(Transaction transaction) {
        return new TransactionUpdateRequestDto(
                transaction.getId(),
                transaction.getDetail(),
                transaction.getTransactionCategory(),
                transaction.getAmount(),
                transaction.getTransactionDate()
        );
    }
}
