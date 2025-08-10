package com.goodstudy.learning.dto;

import lombok.*;
import java.time.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LearningDto {
    private Long id;
    private String learnerName;
    private CourseDto course;
    private LocalDateTime enrolledAt;
}
