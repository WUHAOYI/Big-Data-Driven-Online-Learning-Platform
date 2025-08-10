package com.goodstudy.notification.service.impl;

import com.goodstudy.notification.model.NotificationEntity;
import com.goodstudy.notification.model.dto.NotificationDto;
import com.goodstudy.notification.repository.NotificationEntityRepository;
import com.goodstudy.notification.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationEntityRepository repo;

    public NotificationServiceImpl(NotificationEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public NotificationDto create(String name) {
        NotificationEntity e = NotificationEntity.builder().name(name).build();
        NotificationEntity saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public Optional<NotificationDto> findById(Long id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public List<NotificationDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private NotificationDto toDto(NotificationEntity e) {
        return NotificationDto.builder().id(e.getId()).name(e.getName()).build();
    }
}
