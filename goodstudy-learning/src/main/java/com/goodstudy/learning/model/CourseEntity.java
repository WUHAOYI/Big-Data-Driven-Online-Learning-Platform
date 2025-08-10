package com.goodstudy.learning.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 4000)
    private String description;
    private String level;
    private boolean published;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningEntity> learnings = new ArrayList<>();
}
