package com.naumovets.market.core.converters;

import com.naumovets.market.api.dto.order.OrderDto;
import com.naumovets.market.core.entities.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter itemConverter;

    public OrderDto entityToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .address(order.getAddress())
                .phone(order.getPhone())
                .totalPrice(order.getTotalPrice())
                .username(order.getUsername())
                .items(order.getItems().stream().map(itemConverter::entityToDto).collect(Collectors.toList()))
                .build();
    }
}
