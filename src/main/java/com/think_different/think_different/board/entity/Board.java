package com.think_different.think_different.board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_board")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 제목
    private String contents; // 내용

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt; // 생성시간
    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정시간
}
