package com.naumovets.spring_secutiry.controller;

import com.naumovets.spring_secutiry.cart.Cart;
import com.naumovets.spring_secutiry.cart.CartItem;
import com.naumovets.spring_secutiry.service.cart.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
@Slf4j
public class CartController {

    private CartService cartService;

    @GetMapping
    public Cart getAllProductCart() {
        return cartService.getCart();
    }

    @PostMapping("/{id}")
    public void addProductInCart(@PathVariable Long id) {
        cartService.addProduct(id);
    }

    @PutMapping
    public void changeQuantity(@RequestParam Long id, @RequestParam Integer delta) {
        cartService.changeQuantity(id, delta);
    }

    @DeleteMapping("/{id}")
    public void deleteByIdCart(@PathVariable Long id) {
        cartService.deleteProduct(id);
    }
}
