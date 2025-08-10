package com.goodstudy.payment.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
