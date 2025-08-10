package com.goodstudy.learning.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String recipient;
    private LocalDateTime issuedAt;
    private String title;

    @ManyToOne
    @JoinColumn(name = "learning_id")
    private LearningEntity learning;
}
