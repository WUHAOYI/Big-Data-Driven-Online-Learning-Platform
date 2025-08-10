package com.goodstudy.learning.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressDto {
    private Long id;
    private LearningDto learning;
    private int percentage;
}
