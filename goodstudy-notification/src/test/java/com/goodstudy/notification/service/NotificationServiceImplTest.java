package com.goodstudy.notification.service;

import com.goodstudy.notification.model.NotificationEntity;
import com.goodstudy.notification.model.dto.NotificationDto;
import com.goodstudy.notification.repository.NotificationEntityRepository;
import com.goodstudy.notification.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class NotificationServiceImplTest {

    @Test
    void create_shouldReturnDto() {
        NotificationEntityRepository repo = Mockito.mock(NotificationEntityRepository.class);
        NotificationServiceImpl svc = new NotificationServiceImpl(repo);

        NotificationEntity saved = NotificationEntity.builder().id(1L).name("n").build();
        when(repo.save(any(NotificationEntity.class))).thenReturn(saved);

        NotificationDto dto = svc.create("n");
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("n", dto.getName());
    }

    @Test
    void findById_whenFound_returnDto() {
        NotificationEntityRepository repo = Mockito.mock(NotificationEntityRepository.class);
        NotificationServiceImpl svc = new NotificationServiceImpl(repo);

        NotificationEntity e = NotificationEntity.builder().id(2L).name("x").build();
        when(repo.findById(2L)).thenReturn(Optional.of(e));

        Optional<NotificationDto> od = svc.findById(2L);
        assertTrue(od.isPresent());
        assertEquals("x", od.get().getName());
    }
}
