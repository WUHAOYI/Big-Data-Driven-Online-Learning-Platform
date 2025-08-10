package com.goodstudy.base.service;

import com.goodstudy.base.model.BaseEntity;
import com.goodstudy.base.model.dto.BaseDto;
import com.goodstudy.base.repository.BaseEntityRepository;
import com.goodstudy.base.service.impl.BaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BaseServiceImplTest {

    @Test
    void create_shouldReturnDto() {
        BaseEntityRepository repo = Mockito.mock(BaseEntityRepository.class);
        BaseServiceImpl svc = new BaseServiceImpl(repo);

        BaseEntity saved = BaseEntity.builder().id(1L).name("n").build();
        when(repo.save(any(BaseEntity.class))).thenReturn(saved);

        BaseDto dto = svc.create("n");
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("n", dto.getName());
    }

    @Test
    void findById_whenFound_returnDto() {
        BaseEntityRepository repo = Mockito.mock(BaseEntityRepository.class);
        BaseServiceImpl svc = new BaseServiceImpl(repo);

        BaseEntity e = BaseEntity.builder().id(2L).name("x").build();
        when(repo.findById(2L)).thenReturn(Optional.of(e));

        Optional<BaseDto> od = svc.findById(2L);
        assertTrue(od.isPresent());
        assertEquals("x", od.get().getName());
    }
}
