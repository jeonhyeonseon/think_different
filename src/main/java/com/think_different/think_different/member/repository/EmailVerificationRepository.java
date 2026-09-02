package com.think_different.think_different.member.repository;

import com.think_different.think_different.member.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findTopByEmailOrderByIdDesc(String email);
    boolean existsByEmailAndVerifiedTrue(String email);
    long countByEmailAndCreatedAtAfter(String email, LocalDateTime after);
}
