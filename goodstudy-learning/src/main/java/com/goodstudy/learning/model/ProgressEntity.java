package com.goodstudy.learning.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "progress_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private LearningEntity learning;

    private int percentage; // 进度百分比
}
