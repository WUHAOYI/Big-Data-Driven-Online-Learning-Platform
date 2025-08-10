package com.goodstudy.learning.mapping;

import com.goodstudy.learning.model.*;
import com.goodstudy.learning.dto.*;

public class LearningMapper {
    public static LearningDto toDto(LearningEntity e) {
        if (e == null) return null;
        CourseDto c = e.getCourse() == null ? null : CourseDto.builder()
                .id(e.getCourse().getId())
                .title(e.getCourse().getTitle())
                .build();
        return LearningDto.builder()
                .id(e.getId())
                .learnerName(e.getLearnerName())
                .course(c)
                .enrolledAt(e.getEnrolledAt())
                .build();
    }

    public static LearningEntity fromDto(LearningDto d) {
        if (d == null) return null;
        LearningEntity e = LearningEntity.builder()
                .id(d.getId())
                .learnerName(d.getLearnerName())
                .build();
        return e;
    }
}
