package com.think_different.think_different.chat.chat.dto;

import com.think_different.think_different.chat.chat.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {

    private Long chatroomId; // 채틷방ID
    private Long memberId; // 참여자ID
    private String nickname; // 참여자 닉네임
    private String message; // 메시지
    private LocalDateTime createdAt; // 시간

}
