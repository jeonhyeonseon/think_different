package com.think_different.think_different.member.service;

import com.think_different.think_different.member.dto.MemberRequestDto;
import com.think_different.think_different.member.entity.Member;
import com.think_different.think_different.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void joinMember(MemberRequestDto memberRequestDto) {

        // 아이디 중복 체크
        if(memberRepository.existsByLoginId(memberRequestDto.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // 이메일 중복 체크
        if(memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 엔터티 변환
        Member member = memberRequestDto.toMember();
        // 비밀번호 암호화
        member.encodePassword(passwordEncoder);
        // 저장
        memberRepository.save(member);
    }

    // 아이디 중복 체크
    public boolean existsLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    // 이메일 중복 체크
    public boolean existsEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
