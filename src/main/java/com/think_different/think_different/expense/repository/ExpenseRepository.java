package com.think_different.think_different.expense.repository;

import com.think_different.think_different.expense.domain.Expense;
import io.micrometer.common.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByPaymentDateBetween(LocalDate start, LocalDate end);

    @Query("""
        select distinct function('date_format', e.paymentDate, '%Y-%m')
        from Expense e
        order by function('date_format', e.paymentDate, '%Y-%m')
    
        """)
    List<String> findDistinctYearMonth();
}
