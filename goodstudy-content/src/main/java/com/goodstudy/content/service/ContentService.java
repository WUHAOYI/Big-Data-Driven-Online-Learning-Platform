package com.goodstudy.content.service;

import com.goodstudy.content.model.dto.ContentDto;

import java.util.List;
import java.util.Optional;

public interface ContentService {
    ContentDto create(String name);
    Optional<ContentDto> findById(Long id);
    List<ContentDto> findAll();
    void deleteById(Long id);
}
