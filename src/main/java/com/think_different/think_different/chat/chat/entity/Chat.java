package com.think_different.think_different.chat.chat.entity;

import com.think_different.think_different.chat.chatroom.entity.Chatroom;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_chat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom; // 채팅방 id

    @Column(nullable = false)
    private Long memberId; // 채팅방 멤버 id

    @Column(nullable = false)
    private String nickname; // 채팅방 멤버 닉네임

    @Column(nullable = false)
    private String message; // 메시지 내용

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 생성시간

}
