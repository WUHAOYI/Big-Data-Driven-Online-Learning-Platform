package com.goodstudy.learning.repository;

import com.goodstudy.learning.model.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ExamRepository extends JpaRepository<ExamEntity, Long> {
    List<ExamEntity> findByCourseId(Long courseId);
}
