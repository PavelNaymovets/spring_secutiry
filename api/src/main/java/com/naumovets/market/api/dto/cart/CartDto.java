package com.naumovets.market.api.dto.cart;

import lombok.Data;
import java.util.List;

@Data
public class CartDto {
    private List<CartItemDto> list;
    private int totalPrice;
}