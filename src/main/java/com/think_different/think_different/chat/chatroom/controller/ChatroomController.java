package com.think_different.think_different.chat.chatroom.controller;

import com.think_different.think_different.chat.chatroom.dto.ChatroomRegisterRequestDto;
import com.think_different.think_different.chat.chatroom.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/chatrooms")
@RequiredArgsConstructor
public class ChatroomController {

    private final ChatroomService chatroomService;

    // 등록
    @GetMapping
    public String showRegisterChatroom() {
        log.info("GET: registerChatroom");

        return "chatroom/register";
    }

    @PostMapping
    public String registerChatroom(@ModelAttribute ChatroomRegisterRequestDto chatroomRegisterRequestDto) {
        log.info("POST: registerChatroom");

        chatroomService.registerChatroom(chatroomRegisterRequestDto);

        return "redirect:/chatroom";
    }
}
