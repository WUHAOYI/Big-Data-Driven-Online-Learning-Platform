package com.goodstudy.learning.repository;

import com.goodstudy.learning.model.ProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProgressRepository extends JpaRepository<ProgressEntity, Long> {
    List<ProgressEntity> findByLearningId(Long learningId);
}
