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
public class BoardRegisterRequestDto {

    private String title;
    private String contents;

    // DTO -> Entity로 변환
    public Board toBoard() {
        return Board.builder()
                .title(this.title)
                .contents(this.contents)
                .createAt(LocalDateTime.now())
                .build();
    }
}
