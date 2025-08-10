package com.goodstudy.payment.service.impl;

import com.goodstudy.payment.model.PaymentEntity;
import com.goodstudy.payment.model.dto.PaymentDto;
import com.goodstudy.payment.repository.PaymentEntityRepository;
import com.goodstudy.payment.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentEntityRepository repo;

    public PaymentServiceImpl(PaymentEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public PaymentDto create(String name) {
        PaymentEntity e = PaymentEntity.builder().name(name).build();
        PaymentEntity saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public Optional<PaymentDto> findById(Long id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public List<PaymentDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private PaymentDto toDto(PaymentEntity e) {
        return PaymentDto.builder().id(e.getId()).name(e.getName()).build();
    }
}
