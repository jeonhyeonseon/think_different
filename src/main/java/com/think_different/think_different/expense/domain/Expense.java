package com.think_different.think_different.expense.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_expense")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detail; // 지출내용

    @Enumerated(EnumType.STRING)
    private Category category; // 카테고리

    private String account; // 계좌

    private Long amount; // 지출금액

    @CreatedDate
    @Column(updatable = false)
    private LocalDate paymentDate; // 지출일

    private String memo; // 메모

    public void updateExpense(String detail, Category category, String account, Long amount, LocalDate paymentDate, String memo) {
        this.detail = detail;
        this.category = category;
        this.account = account;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.memo = memo;
    }
}
