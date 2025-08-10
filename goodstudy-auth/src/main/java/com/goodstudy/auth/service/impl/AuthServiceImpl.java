package com.goodstudy.auth.service.impl;

import com.goodstudy.auth.model.AuthEntity;
import com.goodstudy.auth.model.dto.AuthDto;
import com.goodstudy.auth.repository.AuthEntityRepository;
import com.goodstudy.auth.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthEntityRepository repo;

    public AuthServiceImpl(AuthEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public AuthDto create(String name) {
        AuthEntity e = AuthEntity.builder().name(name).build();
        AuthEntity saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public Optional<AuthDto> findById(Long id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public List<AuthDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private AuthDto toDto(AuthEntity e) {
        return AuthDto.builder().id(e.getId()).name(e.getName()).build();
    }
}
