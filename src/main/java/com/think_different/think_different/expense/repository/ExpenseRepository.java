package com.think_different.think_different.expense.repository;

import com.think_different.think_different.expense.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByPaymentDateBetween(LocalDate start, LocalDate end);
}
