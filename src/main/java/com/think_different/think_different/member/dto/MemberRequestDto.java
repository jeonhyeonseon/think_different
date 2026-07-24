package com.think_different.think_different.member.dto;

import com.think_different.think_different.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(
            regexp = "^([가-힣]{2,}|[A-Za-z]{2,})$",
            message = "이름은 한글 또는 영어만 입력 가능합니다. (혼용 불가)"
    )
    private String name; // 이름

    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId; // 로그인 아이디

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$",
            message = "비밀번호는 8자 이상이며 영문, 숫자, 특수문자를 모두 포함해야 합니다."
    )
    private String password; // 비밀번호

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordConfirm; // 비밀번호 확인

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호를 010-XXXX-XXXX 형식으로 모두 입력해주세요.")
    private String phone; // 전화번호

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email; // 이메일

    private Boolean loginIdChecked; // 아이디 중복 체크 완료 여부
    private Boolean emailChecked; // 이메일 중복 체크 완료 여부

    public Member toMember() {
        return Member.builder()
                .name(this.name)
                .loginId(this.loginId)
                .password(this.password)
                .phone(this.phone)
                .email(this.email)
                .build();
    }
}
