package com.think_different.think_different.chat.chat.controller;

import com.think_different.think_different.chat.chat.service.ChatService;
import com.think_different.think_different.chat.chatroom.dto.ChatroomDataInfo;
import com.think_different.think_different.chat.chatroom.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatroomService chatroomService;

    @GetMapping("/{chatroomId}")
    public String showChat(@PathVariable("id") Long chatroomId, Model model) {

        ChatroomDataInfo chatroomDataInfo = chatroomService.getChatroomInfo(chatroomId);

        model.addAttribute("chatroomName", chatroomDataInfo.getTitle());
        model.addAttribute("memberCount", chatroomDataInfo.getMemberCount());
        model.addAttribute("categories", chatroomDataInfo.getCategories());


        return "/chat/list";
    }
}
