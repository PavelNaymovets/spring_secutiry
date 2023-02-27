package com.naumovets.market.core.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoryDto {
    private Long id;
    private String title;
    private List<ProductDto> products;
}
