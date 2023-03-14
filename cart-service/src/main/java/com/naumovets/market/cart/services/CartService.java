package com.naumovets.market.cart.services;


import com.naumovets.market.api.dto.product.ProductDto;
import com.naumovets.market.cart.integrations.ProductServiceIntegration;
import com.naumovets.market.cart.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;

    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;
    private HashMap<String, Cart> carts;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public Cart getCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if(!carts.containsKey(targetUuid)) {
            carts.put(targetUuid, new Cart());
        }

        return carts.get(targetUuid);
    }

    public void addProduct(String uuid,Long productId) {
        ProductDto product = productServiceIntegration.findById(productId);
        getCart(uuid).add(product);
    }

    public void deleteProduct(String uuid,Long productId) {
        ProductDto product = productServiceIntegration.findById(productId);
        getCart(uuid).delete(product);
    }

    public void changeQuantity(String uuid, Long id, Integer delta) {
        getCart(uuid).changeQuantity(id, delta);
    }

    public void deleteProducts(String uuid) {
        getCart(uuid).deleteAll();
    }
}
