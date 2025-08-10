package com.goodstudy.learning.dto;

import lombok.*;
import java.time.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto {
    private Long id;
    private Long examId;
    private Long learningId;
    private Double score;
    private String gradeLetter;
    private LocalDateTime awardedAt;
}
