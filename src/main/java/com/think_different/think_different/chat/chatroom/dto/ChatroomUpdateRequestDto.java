package com.think_different.think_different.chat.chatroom.dto;

import com.think_different.think_different.chat.chatroom.entity.Category;
import com.think_different.think_different.chat.chatroom.entity.Chatroom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomUpdateRequestDto {

    private Long id;
    private String title;
    private String contents;
    private Set<Category> category;
    private LocalDateTime updatedAt;

    public static ChatroomUpdateRequestDto fromChatroom(Chatroom chatroom) {
        return new ChatroomUpdateRequestDto(
                chatroom.getId(),
                chatroom.getTitle(),
                chatroom.getContents(),
                chatroom.getCategory(),
                chatroom.getUpdatedAt()
        );
    }
}
