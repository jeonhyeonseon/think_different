package com.think_different.think_different.board.dto;

import com.think_different.think_different.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequestDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDateTime updatedAt;

    public static BoardUpdateRequestDto fromBoard(Board board) {
        return new BoardUpdateRequestDto(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                board.getUpdatedAt()
        );
    }
}
