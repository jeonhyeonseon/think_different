package com.think_different.think_different.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_email_verification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Builder
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email; // 인증 대상 이메일

    @Column(nullable = false)
    private String code; // 인증코드 6자리

    @Column(nullable = false)
    private LocalDateTime expiresAt; // 인증코드 만료 시각

    @Column(nullable = false)
    private boolean verified; // 인증 완료 여부

    @Column(nullable = false)
    private LocalDateTime createdAt; // 발송 시각 (재발송 제한 계산용)

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    public void verify() {
        this.verified = true;
    }
}
