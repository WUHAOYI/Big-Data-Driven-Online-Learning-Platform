package com.goodstudy.content.repository;

import com.goodstudy.content.model.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentEntityRepository extends JpaRepository<ContentEntity, Long> {
}
