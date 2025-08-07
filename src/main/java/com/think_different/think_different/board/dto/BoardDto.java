package com.think_different.think_different.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String title; // 제목
    private String contents; // 내용

}
