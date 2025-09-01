package com.think_different.think_different.board.controller;

import com.think_different.think_different.board.dto.BoardDetailResponseDto;
import com.think_different.think_different.board.dto.BoardListResponseDto;
import com.think_different.think_different.board.dto.BoardRegisterRequestDto;
import com.think_different.think_different.board.dto.BoardUpdateRequestDto;
import com.think_different.think_different.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 목록
    @GetMapping
    public String listBoard(@RequestParam(required = false) String keyword,
                            @PageableDefault(page = 0, size = 10) Pageable pageable,
                            Model model) {
        log.info("GET: board/listBoard");

        Page<BoardListResponseDto> boardList = boardService.getBoardList(keyword, pageable);

        // 페이지 바 표시
        int page = boardList.getNumber();
        int totalPage = boardList.getTotalPages();
        int barSize = 5;
        int startPage = Math.max(0, (page / barSize) * barSize);
        int endPage = Math.min(totalPage, startPage + barSize);

        model.addAttribute("boardList", boardList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("barSize", barSize);

        return "board/list";
    }

    // 등록
    @GetMapping("/new")
    public String registerBoard() {
        log.info("GET: board/registerBoard");

        return "board/register";
    }

    @PostMapping
    public String actionRegisterBoard(@ModelAttribute BoardRegisterRequestDto boardRegisterRequestDto) {
        log.info("POST: board/actionRegisterBoard");

        boardService.registerBoard(boardRegisterRequestDto);

        return "redirect:/board";
    }

    // 상세
    @GetMapping("/{id}")
    public String detailBoard(@PathVariable Long id, Model model) {
        log.info("GET: board/detailBoard");

        BoardDetailResponseDto boardDetail = boardService.getBoardDetail(id);

        model.addAttribute("boardDetail", boardDetail);
        return "board/detail";
    }

    // 수정
    @GetMapping("/{id}/edit")
    public String updateBoard(@PathVariable Long id, Model model) {
        log.info("GET: board/updateBoard");

        BoardUpdateRequestDto boardUpdate = boardService.getUpdateBoard(id);

        model.addAttribute("boardUpdate", boardUpdate);
        return "board/update";
    }

    @PostMapping("/{id}/edit")
    public String actionUpdateBoard(@PathVariable Long id,
                                    @ModelAttribute BoardUpdateRequestDto boardUpdateRequestDto ) {
        log.info("POST: board/updateBoard");

        boardService.updateBoard(id, boardUpdateRequestDto);

        return "redirect:/board" + id;
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String deleteBoard(@PathVariable Long id) {
        log.info("POST: board/delete");

        boardService.deleteBoard(id);

        return "redirect:/board";
    }
}
