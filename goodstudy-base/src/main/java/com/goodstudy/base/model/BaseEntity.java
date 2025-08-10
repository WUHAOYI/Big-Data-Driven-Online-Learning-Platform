package com.goodstudy.base.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "base_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
