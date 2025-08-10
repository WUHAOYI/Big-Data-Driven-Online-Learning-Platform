package com.goodstudy.learning.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningDto {
    private Long id;
    private String learnerName;
    private CourseDto course;
}
