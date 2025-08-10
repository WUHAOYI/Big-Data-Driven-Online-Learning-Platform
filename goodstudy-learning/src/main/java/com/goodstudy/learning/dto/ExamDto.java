package com.goodstudy.learning.dto;

import lombok.*;
import java.time.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    private Long id;
    private String title;
    private Long courseId;
    private LocalDateTime scheduledAt;
    private int durationMinutes;
    private String instructions;
    private boolean published;
}
