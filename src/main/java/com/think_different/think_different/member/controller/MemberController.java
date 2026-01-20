package com.think_different.think_different.member.controller;

import com.think_different.think_different.member.dto.MemberRequestDto;
import com.think_different.think_different.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @GetMapping("/join")
    public String showJoinForm(Model model) {

        return "members/join";
    }

    @PostMapping
    public String join(@ModelAttribute MemberRequestDto memberRequestDto) {

        memberService.joinMember(memberRequestDto);

        return "redirect:/members/login";
    }

    // 로그인
    @GetMapping("/login")
    public String loginForm() {

        return "members/login";
    }
}
