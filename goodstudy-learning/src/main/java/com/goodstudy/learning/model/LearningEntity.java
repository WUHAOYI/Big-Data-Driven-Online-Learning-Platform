package com.goodstudy.learning.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "learning_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private CourseEntity course; // 所属课程

    private String learnerName;  // 学习者名字
}
