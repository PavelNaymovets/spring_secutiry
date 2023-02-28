package com.naumovets.market.core.validators;


import com.naumovets.market.api.dto.product.ProductDto;
import com.naumovets.market.api.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class ProductValidator {
    public static void validate(ProductDto productDto) {
        List<String> errorMessage = new ArrayList<>();
        if(productDto.getCost() < 1) {
            errorMessage.add("Цена продукта не может быть меньше 1");
        }
        if(productDto.getTitle().isBlank()) {
            errorMessage.add("Продукт не может иметь пустое имя");
        }
        if(!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}
