package com.think_different.think_different.chat.chat.service;

import com.think_different.think_different.chat.chat.dto.ChatDto;
import com.think_different.think_different.chat.chat.entity.Chat;
import com.think_different.think_different.chat.chat.repository.ChatRepository;
import com.think_different.think_different.chat.chatroom.entity.Chatroom;
import com.think_different.think_different.chat.chatroom.repository.ChatroomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatroomRepository chatroomRepository;

    public ChatDto save(ChatDto chatDto) {

        // chatroomId로 chatroom 조회
        Chatroom chatroom = chatroomRepository.findById(chatDto.getChatroomId())
                                            .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        Chat chat = Chat.builder()
                .chatroomId(chatroom)
                .memberId(chatDto.getMemberId())
                .nickname(chatDto.getNickname())
                .message(chatDto.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        // 저장
        Chat saveData = chatRepository.save(chat);

        return ChatDto.builder()
                .chatroomId(saveData.getChatroomId().getId())
                .memberId(saveData.getMemberId())
                .nickname(saveData.getNickname())
                .message(saveData.getMessage())
                .createdAt(saveData.getCreatedAt())
                .build();
    }
}
