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
@Builder
public class BoardDetailResponseDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    public static BoardDetailResponseDto fromBoard(Board board) {
        return new BoardDetailResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                board.getCreateAt()
        );
    }
}
