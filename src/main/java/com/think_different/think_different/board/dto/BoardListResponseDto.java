package com.think_different.think_different.board.dto;

import com.think_different.think_different.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponseDto {

    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public static BoardListResponseDto fromBoard(Board board) {
        return new BoardListResponseDto(
                board.getId(),
                board.getTitle(),
                board.getCreateAt()
        );
    }
}
