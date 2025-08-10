package com.goodstudy.chatbot.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chatbot_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatbotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
