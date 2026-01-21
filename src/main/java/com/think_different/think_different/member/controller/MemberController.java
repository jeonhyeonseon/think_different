package com.think_different.think_different.member.controller;

import com.think_different.think_different.member.dto.MemberRequestDto;
import com.think_different.think_different.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // 아이디 중복 체크
    @ResponseBody
    @GetMapping("/exists/loginId")
    public boolean existsLoginId(@RequestParam String loginId) {
        return memberService.existsLoginId(loginId);
    }

    // 이메일 중복 체크
    @ResponseBody
    @GetMapping("/exists/email")
    public boolean existsEmail(@RequestParam String email) {
        return memberService.existsEmail(email);
    }

    // 로그인
    @GetMapping("/login")
    public String loginForm() {

        return "members/login";
    }
}
