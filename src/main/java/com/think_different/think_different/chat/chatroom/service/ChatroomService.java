package com.think_different.think_different.chat.chatroom.service;

import com.think_different.think_different.chat.chatroom.dto.ChatroomDetailResponseDto;
import com.think_different.think_different.chat.chatroom.dto.ChatroomRegisterRequestDto;
import com.think_different.think_different.chat.chatroom.dto.ChatroomUpdateRequestDto;
import com.think_different.think_different.chat.chatroom.entity.Chatroom;
import com.think_different.think_different.chat.chatroom.repository.ChatroomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;

    public void registerChatroom(ChatroomRegisterRequestDto chatroomRegisterRequestDto) {

        Chatroom chatroom = chatroomRegisterRequestDto.toChatroom();

        chatroomRepository.save(chatroom);
    }

    public ChatroomDetailResponseDto getChatroomDetail(Long id) {

        Chatroom chatroom = chatroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        return ChatroomDetailResponseDto.fromChatroom(chatroom);
    }

    public ChatroomUpdateRequestDto getChatroom(Long id) {

        Chatroom chatroom = chatroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        return ChatroomUpdateRequestDto.fromChatroom(chatroom);
    }

    public void getUpdateChatroom(Long id, ChatroomUpdateRequestDto chatroomUpdateRequestDto) {

        Chatroom chatroom = chatroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        chatroom.updateChatroom(chatroomUpdateRequestDto.getTitle(), chatroomUpdateRequestDto.getContents(), chatroomUpdateRequestDto.getCategory());
    }

    public void deleteChatroom(Long id) {

        Chatroom chatroom = chatroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        chatroomRepository.delete(chatroom);
    }
}
