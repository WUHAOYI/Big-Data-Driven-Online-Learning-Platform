package com.goodstudy.chatbot.repository;

import com.goodstudy.chatbot.model.ChatbotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotEntityRepository extends JpaRepository<ChatbotEntity, Long> {
}
