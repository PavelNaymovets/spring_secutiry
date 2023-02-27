package com.naumovets.market.core.service.cart;

import com.naumovets.market.core.aop.aspects.annotations.Timer;
import com.naumovets.market.core.entities.products.Product;
import com.naumovets.market.core.service.products.ProductService;
import com.naumovets.market.core.cart.Cart;
import com.naumovets.market.core.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Timer
public class CartService {
    private final ProductService productService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public void addProduct(Long productId) {
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Could not add product because did not find, id: " + productId));
        cart.add(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Could not delete product because did not find, id: " + productId));
        cart.delete(product);
    }

    public void changeQuantity(Long id, Integer delta) {
        cart.changeQuantity(id, delta);
    }
}
