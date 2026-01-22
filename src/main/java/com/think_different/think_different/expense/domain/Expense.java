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
    private ExpenseType expenseType; // 타입

    @Enumerated(EnumType.STRING)
    private Category category; // 카테고리

    private String account; // 계좌

    private Long amount; // 지출금액

    @CreatedDate
    @Column(updatable = false)
    private LocalDate paymentDate; // 지출일

    private String memo; // 메모

}
