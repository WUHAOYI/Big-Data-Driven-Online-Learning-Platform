package com.goodstudy.learning.repository;

import com.goodstudy.learning.model.LearningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface LearningRepository extends JpaRepository<LearningEntity, Long> {
    List<LearningEntity> findByLearnerName(String learnerName);
}
