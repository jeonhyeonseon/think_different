package com.think_different.think_different.expense.controller;

import com.think_different.think_different.expense.dto.ExpenseCreateRequestDto;
import com.think_different.think_different.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    // 등록
    @GetMapping("/new")
    public String showCreateExpense() {

        return "expense/create";
    }

    @PostMapping
    public String createExpense(@ModelAttribute ExpenseCreateRequestDto expenseCreateRequestDto) {

        expenseService.createExpense(expenseCreateRequestDto);

        return "redirect:/expense";
    }
}
