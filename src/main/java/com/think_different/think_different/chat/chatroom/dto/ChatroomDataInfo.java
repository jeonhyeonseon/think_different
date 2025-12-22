package com.think_different.think_different.chat.chatroom.dto;

import com.think_different.think_different.chat.chatroom.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatroomDataInfo {

    private String title;
    private Set<Category> categories;
    private int memberCount;

}
