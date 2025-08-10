package com.goodstudy.learning.dto;

import lombok.*;
import java.time.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgressDto {
    private Long id;
    private LearningDto learning;
    private int percentage;
    private LocalDateTime updatedAt;
}
