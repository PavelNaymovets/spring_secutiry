package com.naumovets.market.core.integrations;

import com.naumovets.market.api.dto.cart.CartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartServiceIntegration {
    private final WebClient cartServiceWebclient;

    public CartDto getCart() {
        return cartServiceWebclient.get()
                .uri("/api/v1/cart")
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clearCart() {
        cartServiceWebclient.delete()
                .uri("/api/v1/cart")
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
