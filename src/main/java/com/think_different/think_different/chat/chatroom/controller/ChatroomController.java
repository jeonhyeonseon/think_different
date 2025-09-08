package com.think_different.think_different.chat.chatroom.controller;

import com.think_different.think_different.chat.chatroom.dto.ChatroomDetailResponseDto;
import com.think_different.think_different.chat.chatroom.dto.ChatroomRegisterRequestDto;
import com.think_different.think_different.chat.chatroom.dto.ChatroomUpdateRequestDto;
import com.think_different.think_different.chat.chatroom.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // 상세
    @GetMapping("/{id}")
    public String showDetailChatroom(@PathVariable Long id, Model model) {
        log.info("GET: detailChatroom");

        ChatroomDetailResponseDto chatroomDetail = chatroomService.getChatroomDetail(id);

        model.addAttribute("chatroomDetail", chatroomDetail);

        return "chatroom/detail";
    }

    // 수정
    @GetMapping("/{id}/edit")
    public String showEditChatroom(@PathVariable Long id,
                                   Model model) {
        log.info("GET: updateChatroom");

        ChatroomUpdateRequestDto chatroomUpdate = chatroomService.getChatroom(id);

        model.addAttribute("chatroomUpdate", chatroomUpdate);
        return "chatroom/update";
    }

    @PostMapping("/{id}/edit")
    public String updateChatroom(@PathVariable Long id,
                                 @ModelAttribute ChatroomUpdateRequestDto chatroomUpdateRequestDto) {

        chatroomService.getUpdateChatroom(id, chatroomUpdateRequestDto);

        return "redirect:/chatroom/" + id;
    }
}
