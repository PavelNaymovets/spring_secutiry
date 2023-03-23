package com.naumovets.market.core.validators;

import com.naumovets.market.api.dto.product.ProductDto;

public interface OrderValidator{
    void validate(String username, String phone, String address);
}
