package com.think_different.think_different.board.dto;

import com.think_different.think_different.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponseDto {

    private String title;

    public static BoardListResponseDto fromBoard(Board board) {
        return new BoardListResponseDto(
                board.getTitle()
        );
    }
}
