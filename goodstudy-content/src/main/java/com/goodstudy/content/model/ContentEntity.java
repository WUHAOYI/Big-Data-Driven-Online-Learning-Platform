package com.goodstudy.content.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "content_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
