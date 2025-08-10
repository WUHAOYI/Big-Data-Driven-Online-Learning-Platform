package com.goodstudy.learning.repository;

import com.goodstudy.learning.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findByPublishedTrue();
}
