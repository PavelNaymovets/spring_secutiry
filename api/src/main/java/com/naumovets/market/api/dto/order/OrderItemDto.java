package com.naumovets.market.api.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Модель элемента заказа")
public class OrderItemDto {

    @Schema(description = "id эелемента заказа", required = true, example = "1")
    private Long id;

    @Schema(description = "Название продукта", required = true, maxLength = 255, minLength = 3, example = "Молоко")
    private String productTitle;

    @Schema(description = "id заказа", required = true, example = "1")
    private Long orderId;

    @Schema(description = "количество одного конкретного продукта в заказе", required = true, example = "1")
    private int quantity;

    @Schema(description = "Стоимость одного конкретного продукта", required = true, example = "200.00")
    private BigDecimal pricePerProduct;

    @Schema(description = "Полная стоимость элемента заказа", required = true, example = "200.00")
    private BigDecimal price;
}
