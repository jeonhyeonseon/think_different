package com.think_different.think_different.member.controller;

import com.think_different.think_different.member.dto.MemberRequestDto;
import com.think_different.think_different.member.service.EmailVerificationService;
import com.think_different.think_different.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    private final EmailVerificationService emailVerificationService;

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
        } else if (!Boolean.TRUE.equals(memberRequestDto.getEmailVerified())
                || !emailVerificationService.isVerified(memberRequestDto.getEmail())) {
            bindingResult.rejectValue("email", "notVerified", "이메일 인증을 완료해주세요.");
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

    // 이메일 인증코드 발송 (이메일 중복 체크 포함)
    @ResponseBody
    @PostMapping("/email/send-code")
    public ResponseEntity<String> sendEmailCode(@RequestParam String email) {
        try {
            emailVerificationService.sendVerificationCode(email);
            return ResponseEntity.ok("인증코드가 발송되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 이메일 인증코드 확인
    @ResponseBody
    @PostMapping("/email/verify-code")
    public ResponseEntity<String> verifyEmailCode(@RequestParam String email, @RequestParam String code) {
        try {
            emailVerificationService.verifyCode(email, code);
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인
    @GetMapping("/login")
    public String loginForm() {

        return "members/login";
    }
}
