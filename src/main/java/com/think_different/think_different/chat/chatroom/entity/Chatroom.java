package com.think_different.think_different.chat.chatroom.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tbl_chatroom")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "tbl_chatroom_category",
            joinColumns = @JoinColumn(name = "chatroom_id")
    )
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Set<Category> category;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
