package com.naumovets.market.cart.services;


import com.naumovets.market.api.dto.product.ProductDto;
import com.naumovets.market.cart.integrations.ProductServiceIntegration;
import com.naumovets.market.cart.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public void addProduct(Long productId) {
        ProductDto product = productServiceIntegration.findById(productId);
        cart.add(product);
    }

    public void deleteProduct(Long productId) {
        ProductDto product = productServiceIntegration.findById(productId);
        cart.delete(product);
    }

    public void changeQuantity(Long id, Integer delta) {
        cart.changeQuantity(id, delta);
    }

    public void deleteProducts() {
        cart.deleteAll();
    }
}
