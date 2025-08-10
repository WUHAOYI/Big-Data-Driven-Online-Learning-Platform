package com.goodstudy.learning.dto;

import lombok.*;
import java.time.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDto {
    private Long id;
    private String code;
    private String recipient;
    private LocalDateTime issuedAt;
    private String title;
    private Long learningId;
}
