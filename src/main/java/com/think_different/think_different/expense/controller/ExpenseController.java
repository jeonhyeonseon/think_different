package com.think_different.think_different.expense.controller;

import com.think_different.think_different.expense.dto.ExpenseCreateRequestDto;
import com.think_different.think_different.expense.dto.ExpenseListResponseDto;
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

    @GetMapping
    public String showExpenseList(@RequestParam String month,
                                  Model model) {

        ExpenseListResponseDto listResponseDto = expenseService.listExpense(month);

        model.addAttribute("listResponseDto", listResponseDto);

        return "expense/list";
    }

    @GetMapping("/create")
    public String showCreateExpenseForm() {

        return "expense/create";
    }

    @PostMapping
    public String createExpense(@ModelAttribute ExpenseCreateRequestDto createRequestDto) {

        expenseService.createExpense(createRequestDto);

        return "redirect:/create";
    }

    @GetMapping("/{id}/edit")
    public String showEditExpenseForm(@PathVariable Long id,
                                      Model model) {

        ExpenseUpdateRequestDto updateRequestDto = expenseService.updateExpenseForm(id);

        model.addAttribute("updateRequestDto", updateRequestDto);

        return "expsene/edit";
    }

    @PostMapping("/{id}/edit")
    public String editExpense(@PathVariable Long id,
                              @ModelAttribute ExpenseUpdateRequestDto updateRequestDto) {

        expenseService.updateExpense(id, updateRequestDto);

        return "redirect:/expense/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "redirect:/expense/" + id;
    }
}
