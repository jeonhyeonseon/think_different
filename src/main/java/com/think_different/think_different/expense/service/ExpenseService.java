package com.think_different.think_different.expense.service;

import com.think_different.think_different.expense.domain.Expense;
import com.think_different.think_different.expense.dto.ExpenseCreateRequestDto;
import com.think_different.think_different.expense.dto.ExpenseUpdateRequestDto;
import com.think_different.think_different.expense.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public void createExpense(ExpenseCreateRequestDto createRequestDto) {

        Expense expense = createRequestDto.toExpense();

        expenseRepository.save(expense);
    }
}
