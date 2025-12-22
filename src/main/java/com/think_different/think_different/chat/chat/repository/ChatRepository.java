package com.think_different.think_different.chat.chat.repository;

import com.think_different.think_different.chat.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
