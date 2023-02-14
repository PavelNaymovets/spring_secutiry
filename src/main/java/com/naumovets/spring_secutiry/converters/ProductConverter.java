package com.naumovets.spring_secutiry.converters;

import com.naumovets.spring_secutiry.dto.ProductDto;
import com.naumovets.spring_secutiry.entities.products.Category;
import com.naumovets.spring_secutiry.entities.products.Product;
import com.naumovets.spring_secutiry.exceptions.ResourceNotFoundException;
import com.naumovets.spring_secutiry.service.products.CategoryService;
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
