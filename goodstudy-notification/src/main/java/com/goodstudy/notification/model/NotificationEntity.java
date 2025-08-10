package com.goodstudy.notification.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
