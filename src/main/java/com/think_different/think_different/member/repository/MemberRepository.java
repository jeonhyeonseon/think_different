package com.think_different.think_different.member.repository;

import com.think_different.think_different.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    // 아이디 중복 체크
    boolean existsByLoginId(String loginId);

    // 이메일 중복 체크
    boolean existsByEmail(String email);
}
