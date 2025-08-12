package com.think_different.think_different.board.service;

import com.think_different.think_different.board.dto.BoardListResponseDto;
import com.think_different.think_different.board.dto.BoardRegisterRequestDto;
import com.think_different.think_different.board.entity.Board;
import com.think_different.think_different.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardListResponseDto> findByBoardList() {
        return boardRepository.findAll()
                .stream()
                .map(BoardListResponseDto::fromBoard)
                .toList();
    }

    public void actionRegisterBoard(BoardRegisterRequestDto boardRegisterRequestDto) {
        // DTO를 Entity로 변환하기
        Board board = boardRegisterRequestDto.toBoard();

        boardRepository.save(board);
    }
}
