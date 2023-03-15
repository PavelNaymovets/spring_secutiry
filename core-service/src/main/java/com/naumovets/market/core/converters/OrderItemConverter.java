package com.naumovets.market.core.converters;

import com.naumovets.market.api.dto.order.OrderItemDto;
import com.naumovets.market.core.entities.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemConverter {

    public OrderItemDto entityToDto(OrderItem item) {
        return OrderItemDto.builder()
                .id(item.getId())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .pricePerProduct(item.getPricePerProduct())
                .productTitle(item.getProduct().getTitle())
                .build();
    }
}
