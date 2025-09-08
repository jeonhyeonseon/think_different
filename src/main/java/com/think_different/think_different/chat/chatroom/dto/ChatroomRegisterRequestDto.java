package com.think_different.think_different.chat.chatroom.dto;

import com.think_different.think_different.chat.chatroom.entity.Category;
import com.think_different.think_different.chat.chatroom.entity.Chatroom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatroomRegisterRequestDto {

    private Long id;
    private String title;
    private String contents;
    private Set<Category> category;
    private LocalDateTime createdAt;

    public Chatroom toChatroom() {
        return Chatroom.builder()
                .title(this.title)
                .contents(this.contents)
                .category(this.category)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
