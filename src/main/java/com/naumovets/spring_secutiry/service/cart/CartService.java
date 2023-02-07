package com.naumovets.spring_secutiry.service.cart;

import com.naumovets.spring_secutiry.cart.Cart;
import com.naumovets.spring_secutiry.entities.products.Product;
import com.naumovets.spring_secutiry.exceptions.ResourceNotFoundException;
import com.naumovets.spring_secutiry.service.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
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
