package com.goodstudy.learning.mapping;

import com.goodstudy.learning.model.*;
import com.goodstudy.learning.dto.*;
import org.junit.jupiter.api.*;
import java.time.*;
import static org.junit.jupiter.api.Assertions.*;

public class LearningMapperTest {
    @Test
    public void toDto_and_fromDto() {
        LearningEntity e = LearningEntity.builder().id(3L).learnerName("X").enrolledAt(LocalDateTime.now())
                .course(CourseEntity.builder().id(2L).title("T").build()).build();
        LearningDto d = LearningMapper.toDto(e);
        assertEquals(3L, d.getId());
        LearningEntity back = LearningMapper.fromDto(d);
        assertEquals(d.getLearnerName(), back.getLearnerName());
    }
}
