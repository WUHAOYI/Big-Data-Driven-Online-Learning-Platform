package com.goodstudy.analytics.service.impl;

import com.goodstudy.analytics.model.AnalyticsEntity;
import com.goodstudy.analytics.model.dto.AnalyticsDto;
import com.goodstudy.analytics.repository.AnalyticsEntityRepository;
import com.goodstudy.analytics.service.AnalyticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsEntityRepository repo;

    public AnalyticsServiceImpl(AnalyticsEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public AnalyticsDto create(String name) {
        AnalyticsEntity e = AnalyticsEntity.builder().name(name).build();
        AnalyticsEntity saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public Optional<AnalyticsDto> findById(Long id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public List<AnalyticsDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private AnalyticsDto toDto(AnalyticsEntity e) {
        return AnalyticsDto.builder().id(e.getId()).name(e.getName()).build();
    }
}
