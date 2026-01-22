package com.think_different.think_different.expense.controller;

import com.think_different.think_different.expense.dto.ExpenseCreateRequestDto;
import com.think_different.think_different.expense.dto.ExpenseUpdateRequestDto;
import com.think_different.think_different.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // 수정
    @GetMapping("/{id}/edit")
    public String getUpdateExpense(@PathVariable Long id, Model model) {

        ExpenseUpdateRequestDto expenseUpdateRequestDto = expenseService.getUpdateExpense(id);

        model.addAttribute("updateExpense", expenseUpdateRequestDto);

        return "expense/update";
    }

    @PostMapping("/{id}/edit")
    public String updateExpense(@PathVariable Long id, ExpenseUpdateRequestDto updateRequestDto) {

        expenseService.updateExpense(id, updateRequestDto);

        return "redirect:/expense/" + id;
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "redirect:/expense";
    }

}
