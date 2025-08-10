package com.goodstudy.base.repository;

import com.goodstudy.base.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseEntityRepository extends JpaRepository<BaseEntity, Long> {
}
