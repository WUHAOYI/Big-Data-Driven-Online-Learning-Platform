package com.goodstudy.payment.service;

import com.goodstudy.payment.model.PaymentEntity;
import com.goodstudy.payment.model.dto.PaymentDto;
import com.goodstudy.payment.repository.PaymentEntityRepository;
import com.goodstudy.payment.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PaymentServiceImplTest {

    @Test
    void create_shouldReturnDto() {
        PaymentEntityRepository repo = Mockito.mock(PaymentEntityRepository.class);
        PaymentServiceImpl svc = new PaymentServiceImpl(repo);

        PaymentEntity saved = PaymentEntity.builder().id(1L).name("n").build();
        when(repo.save(any(PaymentEntity.class))).thenReturn(saved);

        PaymentDto dto = svc.create("n");
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("n", dto.getName());
    }

    @Test
    void findById_whenFound_returnDto() {
        PaymentEntityRepository repo = Mockito.mock(PaymentEntityRepository.class);
        PaymentServiceImpl svc = new PaymentServiceImpl(repo);

        PaymentEntity e = PaymentEntity.builder().id(2L).name("x").build();
        when(repo.findById(2L)).thenReturn(Optional.of(e));

        Optional<PaymentDto> od = svc.findById(2L);
        assertTrue(od.isPresent());
        assertEquals("x", od.get().getName());
    }
}
