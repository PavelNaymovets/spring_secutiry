package com.naumovets.market.api.dto.category;

import com.naumovets.market.api.dto.product.ProductDto;
import lombok.Data;
import java.util.List;

@Data
public class CategoryDto {
    private Long id;
    private String title;
    private List<ProductDto> products;
}
