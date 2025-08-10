package com.goodstudy.learning.dto;

import lombok.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedResult<T> {
    private List<T> items;
    private long total;
    private int page;
    private int size;
}
