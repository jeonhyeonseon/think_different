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

        Member member = memberRequestDto.toMember();

        member.encodePassword(passwordEncoder);

        memberRepository.save(member);
    }
}
