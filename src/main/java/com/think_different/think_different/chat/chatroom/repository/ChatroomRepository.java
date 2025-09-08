package com.think_different.think_different.chat.chatroom.repository;

import com.think_different.think_different.chat.chatroom.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
}
