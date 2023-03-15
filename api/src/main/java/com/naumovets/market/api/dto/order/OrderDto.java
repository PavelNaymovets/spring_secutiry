package com.naumovets.market.api.dto.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private String username;
    private List<OrderItemDto> items;
    private String address;
    private String phone;
    private BigDecimal totalPrice;
}
