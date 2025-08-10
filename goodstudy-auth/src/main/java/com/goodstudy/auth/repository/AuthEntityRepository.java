package com.goodstudy.auth.repository;

import com.goodstudy.auth.model.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthEntityRepository extends JpaRepository<AuthEntity, Long> {
}
