package com.goodstudy.learning.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime scheduledAt;
    private int durationMinutes;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @Column(length = 5000)
    private String instructions;

    private boolean published;
}
