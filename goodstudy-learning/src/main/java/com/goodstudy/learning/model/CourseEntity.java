package com.goodstudy.learning.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;        // 课程标题
    private String description;  // 课程描述
    private String level;        // 难度级别，例如 Beginner, Intermediate, Advanced
}
