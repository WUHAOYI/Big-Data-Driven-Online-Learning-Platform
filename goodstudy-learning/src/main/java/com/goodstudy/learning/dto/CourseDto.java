package com.goodstudy.learning.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private String level;
    private boolean published;
}
