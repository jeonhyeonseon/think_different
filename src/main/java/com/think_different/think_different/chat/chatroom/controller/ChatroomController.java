package com.think_different.think_different.chat.chatroom.controller;

import com.think_different.think_different.chat.chatroom.dto.ChatroomDetailResponseDto;
import com.think_different.think_different.chat.chatroom.dto.ChatroomListResponseDto;
import com.think_different.think_different.chat.chatroom.dto.ChatroomRegisterRequestDto;
import com.think_different.think_different.chat.chatroom.dto.ChatroomUpdateRequestDto;
import com.think_different.think_different.chat.chatroom.entity.Category;
import com.think_different.think_different.chat.chatroom.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/chatrooms")
@RequiredArgsConstructor
public class ChatroomController {

    private final ChatroomService chatroomService;

    // 목록
    @GetMapping
    public String showChatroomList(@RequestParam(required = false) String keyword,
                                    @PageableDefault(page = 0, size = 10) Pageable pageable,
                                   Model model) {

        Page<ChatroomListResponseDto> chatroomList = chatroomService.getChatroomList(keyword, pageable);

        // 페이지 바 표시
        int page = chatroomList.getNumber();
        int totalPage = chatroomList.getTotalPages();
        int barSize = 5;
        int startPage = Math.max(0, (page / barSize) * barSize);
        int endPage = Math.min(totalPage, startPage + barSize);

        model.addAttribute("categories", Category.values());
        model.addAttribute("chatroomList", chatroomList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("barSize", barSize);

        return "chatrooms/list";
    }

    // 등록
    @GetMapping("/new")
    public String showRegisterChatroom() {
        log.info("GET: registerChatroom");

        return "chatrooms/register";
    }

    @PostMapping
    public String registerChatroom(@ModelAttribute ChatroomRegisterRequestDto chatroomRegisterRequestDto) {
        log.info("POST: registerChatroom");

        chatroomService.registerChatroom(chatroomRegisterRequestDto);

        return "redirect:/chatrooms";
    }

    // 상세
    @GetMapping("/{id}")
    public String showDetailChatroom(@PathVariable Long id, Model model) {
        log.info("GET: detailChatroom");

        ChatroomDetailResponseDto chatroomDetail = chatroomService.getChatroomDetail(id);

        model.addAttribute("chatroomDetail", chatroomDetail);

        return "chatrooms/detail";
    }

    // 수정
    @GetMapping("/{id}/edit")
    public String showEditChatroom(@PathVariable Long id,
                                   Model model) {
        log.info("GET: updateChatroom");

        ChatroomUpdateRequestDto chatroomUpdate = chatroomService.getChatroom(id);

        model.addAttribute("chatroomUpdate", chatroomUpdate);
        return "chatrooms/update";
    }

    @PostMapping("/{id}/edit")
    public String updateChatroom(@PathVariable Long id,
                                 @ModelAttribute ChatroomUpdateRequestDto chatroomUpdateRequestDto) {

        chatroomService.getUpdateChatroom(id, chatroomUpdateRequestDto);

        return "redirect:/chatrooms/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteChatroom(@PathVariable Long id) {
        log.info("POST: deleteChatroom");

        chatroomService.deleteChatroom(id);

        return "redirect:/chatrooms";
    }
}
