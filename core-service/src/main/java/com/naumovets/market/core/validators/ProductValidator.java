package com.naumovets.market.core.validators;


import com.naumovets.market.api.dto.product.ProductDto;
import com.naumovets.market.api.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator implements Validator<ProductDto> {
    @Override
    public void validate(ProductDto productDto) {
        List<String> errorMessage = new ArrayList<>();
        if (productDto.getCost().compareTo(BigDecimal.valueOf(1)) < 0 || productDto.getCost() == null) {
            errorMessage.add("Цена продукта не может быть меньше 1");
        }
        if (productDto.getTitle().isBlank()) {
            errorMessage.add("Продукт не может иметь пустое имя");
        }
        if (productDto.getTitle().length() < 3 || productDto.getTitle().length() > 255) {
            errorMessage.add("Имя продукта не может быть меньше 3 или больше 255 символов");
        }
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}
