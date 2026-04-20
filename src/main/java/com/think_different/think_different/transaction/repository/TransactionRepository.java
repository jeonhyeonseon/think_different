package com.think_different.think_different.transaction.repository;

import com.think_different.think_different.member.entity.Member;
import com.think_different.think_different.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByMemberAndTransactionDateBetween(Member member, LocalDate start, LocalDate end);

    @Query("""
        select distinct function('date_format', t.transactionDate, '%Y-%m')
        from Transaction t
        order by function('date_format', t.transactionDate, '%Y-%m')
    
        """)
    List<String> findDistinctYearMonth();
}
