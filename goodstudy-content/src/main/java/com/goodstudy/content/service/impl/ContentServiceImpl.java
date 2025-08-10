package com.goodstudy.content.service.impl;

import com.goodstudy.content.model.ContentEntity;
import com.goodstudy.content.model.dto.ContentDto;
import com.goodstudy.content.repository.ContentEntityRepository;
import com.goodstudy.content.service.ContentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    private final ContentEntityRepository repo;

    public ContentServiceImpl(ContentEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public ContentDto create(String name) {
        ContentEntity e = ContentEntity.builder().name(name).build();
        ContentEntity saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public Optional<ContentDto> findById(Long id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public List<ContentDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private ContentDto toDto(ContentEntity e) {
        return ContentDto.builder().id(e.getId()).name(e.getName()).build();
    }
}
