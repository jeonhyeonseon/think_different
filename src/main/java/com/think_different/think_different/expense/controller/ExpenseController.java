package com.think_different.think_different.expense.controller;

import com.think_different.think_different.expense.domain.Category;
import com.think_different.think_different.expense.dto.ExpenseCreateRequestDto;
import com.think_different.think_different.expense.dto.ExpenseListResponseDto;
import com.think_different.think_different.expense.dto.ExpenseUpdateRequestDto;
import com.think_different.think_different.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public String showExpenseList(@RequestParam(required = false) String month,
                                  Model model) {

        YearMonth current = (month == null || month.isBlank())
                            ? YearMonth.now() : YearMonth.parse(month);

        ExpenseListResponseDto listResponseDto = expenseService.listExpense(current);

        List<YearMonth> writtenMonth = expenseService.findWrittenMonth();

        YearMonth prevMonth = null;
        YearMonth nextMonth = null;

        int indexMonth = writtenMonth.indexOf(current);
        if (indexMonth != -1) {
            if (indexMonth > 0) prevMonth = writtenMonth.get(indexMonth - 1);
            if (indexMonth < writtenMonth.size() - 1) nextMonth = writtenMonth.get(indexMonth + 1);
        }

        model.addAttribute("listResponseDto", listResponseDto);
        model.addAttribute("prevMonth",prevMonth);
        model.addAttribute("nextMonth", nextMonth);

        return "expense/list";
    }

    @GetMapping("/create")
    public String showCreateExpenseForm() {

        return "expense/create";
    }

    @PostMapping
    public String createExpense(@ModelAttribute ExpenseCreateRequestDto createRequestDto) {

        expenseService.createExpense(createRequestDto);

        return "redirect:/expense";
    }

    @GetMapping("/{id}")
    public String showEditExpenseForm(@PathVariable Long id,
                                      Model model) {

        ExpenseUpdateRequestDto updateRequestDto = expenseService.updateExpenseForm(id);

        model.addAttribute("categories", Category.values());
        model.addAttribute("updateRequestDto", updateRequestDto);

        return "expense/edit";
    }

    @PostMapping("/{id}/edit")
    public String editExpense(@PathVariable Long id,
                              @ModelAttribute ExpenseUpdateRequestDto updateRequestDto) {

        expenseService.updateExpense(id, updateRequestDto);

        return "redirect:/expense";
    }

    @PostMapping("/{id}/delete")
    public String deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "redirect:/expense";
    }
}
