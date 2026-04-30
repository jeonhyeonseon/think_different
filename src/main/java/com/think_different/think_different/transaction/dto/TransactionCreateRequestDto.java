package com.think_different.think_different.transaction.dto;

import com.think_different.think_different.member.entity.Member;
import com.think_different.think_different.transaction.domain.Transaction;
import com.think_different.think_different.transaction.domain.TransactionCategory;
import com.think_different.think_different.transaction.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreateRequestDto {

    private String detail;
    private TransactionType transactionType;
    private TransactionCategory transactionCategory;
    private Long amount;
    private LocalDate transactionDate;

    public Transaction toTransaction(Member member) {
        return Transaction.builder()
                .detail(detail)
                .transactionType(transactionCategory.getType())
                .transactionCategory(transactionCategory)
                .amount(amount)
                .transactionDate(transactionDate)
                .member(member)
                .build();
    }
}
