package com.goodstudy.learning.repository;

import com.goodstudy.learning.model.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
    List<GradeEntity> findByLearningId(Long learningId);
}
