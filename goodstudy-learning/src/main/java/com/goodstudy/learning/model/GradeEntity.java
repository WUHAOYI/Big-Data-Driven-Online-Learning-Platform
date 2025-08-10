package com.goodstudy.learning.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;
    private String gradeLetter;
    private LocalDateTime awardedAt;

    @ManyToOne
    @JoinColumn(name = "learning_id")
    private LearningEntity learning;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private ExamEntity exam;
}
