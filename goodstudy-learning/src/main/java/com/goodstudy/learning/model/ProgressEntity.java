package com.goodstudy.learning.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int percentage;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "learning_id")
    private LearningEntity learning;

    @PrePersist
    @PreUpdate
    public void touch() {
        updatedAt = LocalDateTime.now();
    }
}
