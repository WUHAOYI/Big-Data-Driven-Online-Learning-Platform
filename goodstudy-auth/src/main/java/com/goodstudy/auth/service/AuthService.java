package com.goodstudy.auth.service;

import com.goodstudy.auth.model.dto.AuthDto;

import java.util.List;
import java.util.Optional;

public interface AuthService {
    AuthDto create(String name);
    Optional<AuthDto> findById(Long id);
    List<AuthDto> findAll();
    void deleteById(Long id);
}
