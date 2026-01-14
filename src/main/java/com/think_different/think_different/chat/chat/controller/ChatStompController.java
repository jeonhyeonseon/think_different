package com.think_different.think_different.chat.chat.controller;

import com.think_different.think_different.chat.chat.dto.ChatDto;
import com.think_different.think_different.chat.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatStompController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chatrooms/{chatroomId}/chat") // /pub로 들어오는 메시지를 서버 메소드로 라우팅
    public void chat(@DestinationVariable Long chatroomId, ChatDto chatDto) {
        if(chatDto.getMessage() == null || chatDto.getMessage().isBlank()) {
            return;
        }

        chatDto.setChatroomId(chatroomId);
        ChatDto saveChatData = chatService.save(chatDto);
        simpMessagingTemplate.convertAndSend("/sub/chatrooms/" + chatroomId, saveChatData); // /sub로 publish해서 구독자에게 전달
    }
}
