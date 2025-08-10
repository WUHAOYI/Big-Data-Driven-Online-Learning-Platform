package com.goodstudy.learning.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "learnings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String learnerName;
    private LocalDateTime enrolledAt;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @PrePersist
    public void prePersist() {
        if (enrolledAt == null) enrolledAt = LocalDateTime.now();
    }
}
