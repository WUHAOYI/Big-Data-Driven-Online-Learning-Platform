package com.goodstudy.notification.service;

import com.goodstudy.notification.model.dto.NotificationDto;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    NotificationDto create(String name);
    Optional<NotificationDto> findById(Long id);
    List<NotificationDto> findAll();
    void deleteById(Long id);
}
