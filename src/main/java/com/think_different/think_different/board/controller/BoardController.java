package com.think_different.think_different.board.controller;

import com.think_different.think_different.board.dto.BoardListResponseDto;
import com.think_different.think_different.board.dto.BoardRegisterRequestDto;
import com.think_different.think_different.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 목록
    @GetMapping("/listBoard")
    public String listBoard(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                                Model model) {
        log.info("GET: board/listBoard");

        Page<BoardListResponseDto> boardList = boardService.findByBoardList(pageable);
        model.addAttribute("boardList", boardList);

        return "board/list";
    }

    // 등록
    @GetMapping("/registerBoard")
    public String registerBoard() {
        log.info("GET: board/registerBoard");

        return "board/register";
    }

    @PostMapping("/actionRegisterBoard")
    public String actionRegisterBoard(@ModelAttribute BoardRegisterRequestDto boardRegisterRequestDto,
                                      RedirectAttributes redirectAttributes) {
        log.info("POST: board/actionRegisterBoard");

        boardService.actionRegisterBoard(boardRegisterRequestDto);
        redirectAttributes.addFlashAttribute("message", "게시글이 등록되었습니다.");

        return "redirect:/board/actionRegisterBoard";
    }
}
