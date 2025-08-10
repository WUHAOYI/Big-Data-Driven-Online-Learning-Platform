package com.goodstudy.learning.repository;

import com.goodstudy.learning.model.ProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<ProgressEntity, Long> {}
