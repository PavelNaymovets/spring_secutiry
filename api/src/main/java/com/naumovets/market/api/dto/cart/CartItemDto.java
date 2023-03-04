package com.naumovets.market.api.dto.cart;

import lombok.Data;

@Data
public class CartItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private int pricePerProduct;
    private int price;
}
