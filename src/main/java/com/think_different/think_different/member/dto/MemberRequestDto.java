package com.think_different.think_different.member.dto;

import com.think_different.think_different.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    private String name; // 이름
    private String loginId; // 로그인 아이디
    private String password; // 비밀번호
    private String phone; // 전화번호
    private String email; // 이메일

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
