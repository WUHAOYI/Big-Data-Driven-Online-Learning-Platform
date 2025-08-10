package com.goodstudy.content.service;

import com.goodstudy.content.model.ContentEntity;
import com.goodstudy.content.model.dto.ContentDto;
import com.goodstudy.content.repository.ContentEntityRepository;
import com.goodstudy.content.service.impl.ContentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ContentServiceImplTest {

    @Test
    void create_shouldReturnDto() {
        ContentEntityRepository repo = Mockito.mock(ContentEntityRepository.class);
        ContentServiceImpl svc = new ContentServiceImpl(repo);

        ContentEntity saved = ContentEntity.builder().id(1L).name("n").build();
        when(repo.save(any(ContentEntity.class))).thenReturn(saved);

        ContentDto dto = svc.create("n");
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("n", dto.getName());
    }

    @Test
    void findById_whenFound_returnDto() {
        ContentEntityRepository repo = Mockito.mock(ContentEntityRepository.class);
        ContentServiceImpl svc = new ContentServiceImpl(repo);

        ContentEntity e = ContentEntity.builder().id(2L).name("x").build();
        when(repo.findById(2L)).thenReturn(Optional.of(e));

        Optional<ContentDto> od = svc.findById(2L);
        assertTrue(od.isPresent());
        assertEquals("x", od.get().getName());
    }
}
