package com.naumovets.market.api.dto.category;

import com.naumovets.market.api.dto.product.ProductDto;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CategoryDto {
    private Long id;
    private String title;
}
