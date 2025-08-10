package com.goodstudy.chatbot.service;

import com.goodstudy.chatbot.model.dto.ChatbotDto;

import java.util.List;
import java.util.Optional;

public interface ChatbotService {
    ChatbotDto create(String name);
    Optional<ChatbotDto> findById(Long id);
    List<ChatbotDto> findAll();
    void deleteById(Long id);
}
