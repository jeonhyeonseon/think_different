package com.think_different.think_different.user.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 이름

    @Column(nullable = false, unique = true)
    private String userId; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String phone; // 전화번호

    @Column(nullable = false, unique = true)
    private String email; // 이메일

}
