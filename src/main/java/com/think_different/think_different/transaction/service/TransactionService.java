package com.think_different.think_different.transaction.service;

import com.think_different.think_different.transaction.domain.Transaction;
import com.think_different.think_different.transaction.dto.TransactionCreateRequestDto;
import com.think_different.think_different.transaction.dto.TransactionListResponseDto;
import com.think_different.think_different.transaction.dto.TransactionUpdateRequestDto;
import com.think_different.think_different.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionListResponseDto listExpense(YearMonth yearMonth) {
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        List<Transaction> transactionList = transactionRepository.findByTransactionDateBetween(start, end);

        long totalAmount = transactionList.stream()
                                       .mapToLong(Transaction::getAmount)
                                       .sum();

        return TransactionListResponseDto.fromExpense(transactionList, totalAmount, yearMonth);
    }

    public List<YearMonth> findWrittenMonth() {
        return transactionRepository.findDistinctYearMonth()
                                .stream()
                                .map(YearMonth::parse)
                                .toList();
    }

    public void createExpense(TransactionCreateRequestDto createRequestDto) {

        Transaction transaction = createRequestDto.toExpense();

        transactionRepository.save(transaction);
    }

    public TransactionUpdateRequestDto updateExpenseForm(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출입니다."));

        return TransactionUpdateRequestDto.fromExpense(transaction);
    }

    public void updateExpense(Long id, TransactionUpdateRequestDto updateRequestDto) {

        Transaction transaction = transactionRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출입니다."));

        transaction.updateExpense(updateRequestDto.getDetail(),
                              updateRequestDto.getTransactionCategory(),
                              updateRequestDto.getAmount(),
                              updateRequestDto.getPaymentDate());
    }

    public void deleteExpense(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출입니다."));

        transactionRepository.delete(transaction);
    }
}
