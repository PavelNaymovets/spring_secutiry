package com.naumovets.market.core.converters;

import com.naumovets.market.api.dto.category.CategoryDto;
import com.naumovets.market.core.entities.products.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public CategoryDto entityToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .build();
    }
}
