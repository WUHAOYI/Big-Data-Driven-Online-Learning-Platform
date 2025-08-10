package com.goodstudy.base.service;

import com.goodstudy.base.model.dto.BaseDto;

import java.util.List;
import java.util.Optional;

public interface BaseService {
    BaseDto create(String name);
    Optional<BaseDto> findById(Long id);
    List<BaseDto> findAll();
    void deleteById(Long id);
}
