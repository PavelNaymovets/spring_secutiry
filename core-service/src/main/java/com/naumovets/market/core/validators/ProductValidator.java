package com.naumovets.market.core.validators;

import com.naumovets.market.api.dto.product.ProductDto;

public interface ProductValidator extends Validator<ProductDto>{
    @Override
    void validate(ProductDto productDto);
}
