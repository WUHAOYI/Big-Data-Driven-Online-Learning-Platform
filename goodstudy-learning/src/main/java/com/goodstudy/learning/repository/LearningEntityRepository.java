package com.goodstudy.learning.repository;

import com.goodstudy.learning.model.LearningEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningEntityRepository extends JpaRepository<LearningEntity, Long> {}
