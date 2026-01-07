package com.think_different.think_different.chat.chatroom.dto;

import com.think_different.think_different.chat.chatroom.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatroomListResponseDto {

    private Long id;
    private String title;
    private Set<Category> categories;
    private LocalDateTime createdAt;
}
