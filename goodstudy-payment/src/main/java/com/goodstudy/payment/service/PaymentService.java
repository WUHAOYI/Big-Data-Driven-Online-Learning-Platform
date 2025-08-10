package com.goodstudy.payment.service;

import com.goodstudy.payment.model.dto.PaymentDto;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    PaymentDto create(String name);
    Optional<PaymentDto> findById(Long id);
    List<PaymentDto> findAll();
    void deleteById(Long id);
}
