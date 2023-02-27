package com.naumovets.market.core.converters;

import com.naumovets.market.core.entities.products.Category;
import com.naumovets.market.core.entities.products.Product;
import com.naumovets.market.core.service.products.CategoryService;
import com.naumovets.market.core.dto.ProductDto;
import com.naumovets.market.core.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public Product dtoToEntity(ProductDto productDto) {
        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория не найдена"));
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getCost(),category);
    }

    public static ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getCost(), product.getCategory().getTitle());
    }

}
