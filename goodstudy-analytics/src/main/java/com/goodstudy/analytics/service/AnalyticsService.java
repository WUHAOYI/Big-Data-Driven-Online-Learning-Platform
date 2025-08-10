package com.goodstudy.analytics.service;

import com.goodstudy.analytics.model.dto.AnalyticsDto;

import java.util.List;
import java.util.Optional;

public interface AnalyticsService {
    AnalyticsDto create(String name);
    Optional<AnalyticsDto> findById(Long id);
    List<AnalyticsDto> findAll();
    void deleteById(Long id);
}
