package com.goodstudy.auth.service;

import com.goodstudy.auth.model.AuthEntity;
import com.goodstudy.auth.model.dto.AuthDto;
import com.goodstudy.auth.repository.AuthEntityRepository;
import com.goodstudy.auth.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

    @Test
    void create_shouldReturnDto() {
        AuthEntityRepository repo = Mockito.mock(AuthEntityRepository.class);
        AuthServiceImpl svc = new AuthServiceImpl(repo);

        AuthEntity saved = AuthEntity.builder().id(1L).name("n").build();
        when(repo.save(any(AuthEntity.class))).thenReturn(saved);

        AuthDto dto = svc.create("n");
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("n", dto.getName());
    }

    @Test
    void findById_whenFound_returnDto() {
        AuthEntityRepository repo = Mockito.mock(AuthEntityRepository.class);
        AuthServiceImpl svc = new AuthServiceImpl(repo);

        AuthEntity e = AuthEntity.builder().id(2L).name("x").build();
        when(repo.findById(2L)).thenReturn(Optional.of(e));

        Optional<AuthDto> od = svc.findById(2L);
        assertTrue(od.isPresent());
        assertEquals("x", od.get().getName());
    }
}
