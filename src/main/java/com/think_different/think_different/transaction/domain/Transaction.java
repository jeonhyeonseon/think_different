package com.think_different.think_different.transaction.domain;

import com.think_different.think_different.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionCategory transactionCategory; // 카테고리

    @Column(nullable = false, length = 100)
    private String detail; // 지출내용

    private Long amount; // 지출금액

    @Column(nullable = false)
    private LocalDate transactionDate; // 지출일


    public void updateExpense(String detail, TransactionCategory transactionCategory, Long amount, LocalDate transactionDate) {
        this.detail = detail;
        this.transactionCategory = transactionCategory;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
}
