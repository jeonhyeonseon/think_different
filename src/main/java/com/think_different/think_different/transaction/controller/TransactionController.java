package com.think_different.think_different.transaction.controller;

import com.think_different.think_different.config.webSecurity.CustomUserDetails;
import com.think_different.think_different.member.entity.Member;
import com.think_different.think_different.transaction.domain.TransactionCategory;
import com.think_different.think_different.transaction.dto.TransactionCreateRequestDto;
import com.think_different.think_different.transaction.dto.TransactionListResponseDto;
import com.think_different.think_different.transaction.dto.TransactionUpdateRequestDto;
import com.think_different.think_different.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/expense")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public String showExpenseList(@RequestParam(required = false) String month,
                                  Model model) {

        YearMonth current = (month == null || month.isBlank())
                            ? YearMonth.now() : YearMonth.parse(month);

        TransactionListResponseDto listResponseDto = transactionService.listExpense(current);

        List<YearMonth> writtenMonth = transactionService.findWrittenMonth();

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
    public String createTransaction(@ModelAttribute TransactionCreateRequestDto createRequestDto,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Member member = customUserDetails.getMember();

        transactionService.createTransaction(createRequestDto, member);

        return "redirect:/expense";
    }

    @GetMapping("/{id}")
    public String showEditExpenseForm(@PathVariable Long id,
                                      Model model) {

        TransactionUpdateRequestDto updateRequestDto = transactionService.updateExpenseForm(id);

        model.addAttribute("categories", TransactionCategory.values());
        model.addAttribute("updateRequestDto", updateRequestDto);

        return "expense/edit";
    }

    @PostMapping("/{id}/edit")
    public String editExpense(@PathVariable Long id,
                              @ModelAttribute TransactionUpdateRequestDto updateRequestDto) {

        transactionService.updateExpense(id, updateRequestDto);

        return "redirect:/expense";
    }

    @PostMapping("/{id}/delete")
    public String deleteExpense(@PathVariable Long id) {

        transactionService.deleteExpense(id);

        return "redirect:/expense";
    }
}
