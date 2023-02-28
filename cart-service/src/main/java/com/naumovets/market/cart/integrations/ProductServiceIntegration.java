package com.naumovets.market.cart.integrations;

import com.naumovets.market.api.dto.product.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final RestTemplate restTemplate;

    public Optional<ProductDto> findById(Long id) {
        return Optional.ofNullable(restTemplate.getForObject("http://localhost:8190/market-core/api/v1/products/" + id, ProductDto.class));
    }
}
