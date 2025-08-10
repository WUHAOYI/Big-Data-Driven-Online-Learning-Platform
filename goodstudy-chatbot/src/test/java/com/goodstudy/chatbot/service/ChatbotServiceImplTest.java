package com.goodstudy.chatbot.service;

import com.goodstudy.chatbot.model.ChatbotEntity;
import com.goodstudy.chatbot.model.dto.ChatbotDto;
import com.goodstudy.chatbot.repository.ChatbotEntityRepository;
import com.goodstudy.chatbot.service.impl.ChatbotServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ChatbotServiceImplTest {

    @Test
    void create_shouldReturnDto() {
        ChatbotEntityRepository repo = Mockito.mock(ChatbotEntityRepository.class);
        ChatbotServiceImpl svc = new ChatbotServiceImpl(repo);

        ChatbotEntity saved = ChatbotEntity.builder().id(1L).name("n").build();
        when(repo.save(any(ChatbotEntity.class))).thenReturn(saved);

        ChatbotDto dto = svc.create("n");
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("n", dto.getName());
    }

    @Test
    void findById_whenFound_returnDto() {
        ChatbotEntityRepository repo = Mockito.mock(ChatbotEntityRepository.class);
        ChatbotServiceImpl svc = new ChatbotServiceImpl(repo);

        ChatbotEntity e = ChatbotEntity.builder().id(2L).name("x").build();
        when(repo.findById(2L)).thenReturn(Optional.of(e));

        Optional<ChatbotDto> od = svc.findById(2L);
        assertTrue(od.isPresent());
        assertEquals("x", od.get().getName());
    }
}
