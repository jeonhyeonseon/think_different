package com.think_different.think_different.board.controller;

import com.think_different.think_different.board.dto.BoardListResponseDto;
import com.think_different.think_different.board.dto.BoardRegisterRequestDto;
import com.think_different.think_different.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 목록
    @GetMapping("/listBoard")
    public List<BoardListResponseDto> listBoard() {
        log.info("GET: board/listBoard");

        List<BoardListResponseDto> boardList = boardService.findByBoardList();

        return boardList;
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
