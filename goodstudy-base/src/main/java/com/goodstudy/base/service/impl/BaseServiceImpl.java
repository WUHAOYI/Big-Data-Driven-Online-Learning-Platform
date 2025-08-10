package com.goodstudy.base.service.impl;

import com.goodstudy.base.model.BaseEntity;
import com.goodstudy.base.model.dto.BaseDto;
import com.goodstudy.base.repository.BaseEntityRepository;
import com.goodstudy.base.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BaseServiceImpl implements BaseService {

    private final BaseEntityRepository repo;

    public BaseServiceImpl(BaseEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public BaseDto create(String name) {
        BaseEntity e = BaseEntity.builder().name(name).build();
        BaseEntity saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public Optional<BaseDto> findById(Long id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public List<BaseDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private BaseDto toDto(BaseEntity e) {
        return BaseDto.builder().id(e.getId()).name(e.getName()).build();
    }
}
