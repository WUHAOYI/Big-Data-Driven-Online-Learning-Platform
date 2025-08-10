package com.goodstudy.analytics.service;

import com.goodstudy.analytics.model.AnalyticsEntity;
import com.goodstudy.analytics.model.dto.AnalyticsDto;
import com.goodstudy.analytics.repository.AnalyticsEntityRepository;
import com.goodstudy.analytics.service.impl.AnalyticsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AnalyticsServiceImplTest {

    @Test
    void create_shouldReturnDto() {
        AnalyticsEntityRepository repo = Mockito.mock(AnalyticsEntityRepository.class);
        AnalyticsServiceImpl svc = new AnalyticsServiceImpl(repo);

        AnalyticsEntity saved = AnalyticsEntity.builder().id(1L).name("n").build();
        when(repo.save(any(AnalyticsEntity.class))).thenReturn(saved);

        AnalyticsDto dto = svc.create("n");
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("n", dto.getName());
    }

    @Test
    void findById_whenFound_returnDto() {
        AnalyticsEntityRepository repo = Mockito.mock(AnalyticsEntityRepository.class);
        AnalyticsServiceImpl svc = new AnalyticsServiceImpl(repo);

        AnalyticsEntity e = AnalyticsEntity.builder().id(2L).name("x").build();
        when(repo.findById(2L)).thenReturn(Optional.of(e));

        Optional<AnalyticsDto> od = svc.findById(2L);
        assertTrue(od.isPresent());
        assertEquals("x", od.get().getName());
    }
}
