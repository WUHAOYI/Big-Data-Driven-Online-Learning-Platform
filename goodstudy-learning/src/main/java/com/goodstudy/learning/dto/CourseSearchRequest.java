package com.goodstudy.learning.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSearchRequest {
    private String q;
    private String level;
    private Boolean published;
    private int page = 0;
    private int size = 20;
    private String sortBy = "id";
    private boolean desc = false;
}
