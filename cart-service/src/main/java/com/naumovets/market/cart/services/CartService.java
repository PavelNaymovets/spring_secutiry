package com.naumovets.market.cart.services;


import com.naumovets.market.api.dto.product.ProductDto;
import com.naumovets.market.cart.integrations.ProductServiceIntegration;
import com.naumovets.market.cart.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    public Cart getCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }

        return (Cart) redisTemplate.opsForValue().get(targetUuid);
    }

    public void addProduct(String uuid,Long productId) {
        ProductDto product = productServiceIntegration.findById(productId);
        execute(uuid, cart -> cart.add(product));
    }

    public void deleteProduct(String uuid,Long productId) {
        ProductDto product = productServiceIntegration.findById(productId);
        execute(uuid, cart -> cart.delete(product));
    }

    public void changeQuantity(String uuid, Long id, Integer delta) {
        execute(uuid, cart -> cart.changeQuantity(id, delta));
    }

    public void deleteProducts(String uuid) {
        execute(uuid, Cart::deleteAll);
    }

    private void execute(String uuid, Consumer<Cart> operation) {
        Cart cart = getCart(uuid);
        operation.accept(cart);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }
}
