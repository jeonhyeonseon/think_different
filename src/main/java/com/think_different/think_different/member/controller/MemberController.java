package com.think_different.think_different.member.controller;

import com.think_different.think_different.member.dto.MemberRequestDto;
import com.think_different.think_different.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
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
        model.addAttribute("memberRequestDto", new MemberRequestDto());
        return "members/join";
    }

    @PostMapping
    public String join(@Valid @ModelAttribute("memberRequestDto") MemberRequestDto memberRequestDto,
                        BindingResult bindingResult) {

        if (StringUtils.hasText(memberRequestDto.getPassword())
                && StringUtils.hasText(memberRequestDto.getPasswordConfirm())
                && !memberRequestDto.getPassword().equals(memberRequestDto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "mismatch", "비밀번호가 일치하지 않습니다.");
        }

        boolean loginIdDuplicate = StringUtils.hasText(memberRequestDto.getLoginId())
                && memberService.existsLoginId(memberRequestDto.getLoginId());
        if (loginIdDuplicate) {
            bindingResult.rejectValue("loginId", "duplicate", "이미 사용 중인 아이디입니다.");
        } else if (!Boolean.TRUE.equals(memberRequestDto.getLoginIdChecked())) {
            bindingResult.rejectValue("loginId", "notChecked", "아이디 중복 체크를 완료해주세요.");
        }

        boolean emailDuplicate = StringUtils.hasText(memberRequestDto.getEmail())
                && memberService.existsEmail(memberRequestDto.getEmail());
        if (emailDuplicate) {
            bindingResult.rejectValue("email", "duplicate", "이미 사용 중인 이메일입니다.");
        } else if (!Boolean.TRUE.equals(memberRequestDto.getEmailChecked())) {
            bindingResult.rejectValue("email", "notChecked", "이메일 중복 체크를 완료해주세요.");
        }

        if (bindingResult.hasErrors()) {
            return "members/join";
        }

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
