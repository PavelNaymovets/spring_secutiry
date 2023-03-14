package com.naumovets.market.cart.controllers;

import com.naumovets.market.api.dto.StringResponse;
import com.naumovets.market.api.dto.cart.CartDto;
import com.naumovets.market.cart.converters.CartConverter;
import com.naumovets.market.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/uuid")
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}")
    public CartDto getAllProductCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        String targetUuid = getCartUuid(username, uuid);
        return cartConverter.entityToDto(cartService.getCart(targetUuid));
    }

    @PostMapping("/{uuid}/{id}")
    public void addProductInCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.addProduct(targetUuid, id);
    }

    @PutMapping("/{uuid}")
    public void changeQuantityProductInCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @RequestParam Long id, @RequestParam Integer delta) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.changeQuantity(targetUuid, id, delta);
    }

    @DeleteMapping("/{uuid}/{id}")
    public void deleteProductByIdFromCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.deleteProduct(targetUuid, id);
    }

    @DeleteMapping("/{uuid}")
    public void deleteProductsFromCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.deleteProducts(targetUuid);
    }

    private String getCartUuid(String username, String uuid) {
        if(username != null) {
            return username;
        }

        return uuid;
    }
}
