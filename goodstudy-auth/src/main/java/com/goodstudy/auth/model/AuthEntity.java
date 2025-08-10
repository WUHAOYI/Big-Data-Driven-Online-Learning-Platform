package com.goodstudy.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auth_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
