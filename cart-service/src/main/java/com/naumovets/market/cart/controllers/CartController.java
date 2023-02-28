package com.naumovets.market.cart.controllers;

import com.naumovets.market.api.dto.cart.CartDto;
import com.naumovets.market.cart.converters.CartConverter;
import com.naumovets.market.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getAllProductCart() {
        return cartConverter.entityToDto(cartService.getCart());
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
