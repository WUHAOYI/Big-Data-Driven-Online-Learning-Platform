package com.goodstudy.chatbot.service.impl;

import com.goodstudy.chatbot.model.ChatbotEntity;
import com.goodstudy.chatbot.model.dto.ChatbotDto;
import com.goodstudy.chatbot.repository.ChatbotEntityRepository;
import com.goodstudy.chatbot.service.ChatbotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatbotServiceImpl implements ChatbotService {

    private final ChatbotEntityRepository repo;

    public ChatbotServiceImpl(ChatbotEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public ChatbotDto create(String name) {
        ChatbotEntity e = ChatbotEntity.builder().name(name).build();
        ChatbotEntity saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public Optional<ChatbotDto> findById(Long id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public List<ChatbotDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private ChatbotDto toDto(ChatbotEntity e) {
        return ChatbotDto.builder().id(e.getId()).name(e.getName()).build();
    }
}
