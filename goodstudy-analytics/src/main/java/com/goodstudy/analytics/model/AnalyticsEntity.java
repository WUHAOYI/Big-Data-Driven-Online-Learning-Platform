package com.goodstudy.analytics.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "analytics_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
