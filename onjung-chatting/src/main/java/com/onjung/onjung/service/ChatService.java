package com.onjung.onjung.repository;

import com.onjung.onjung.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatService extends JpaRepository<Chat, String> {
}
