package com.naumovets.market.api.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "Модель заказа")
public class OrderDto {

    @Schema(description = "id заказа", required = true, example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", required = true, maxLength = 255, minLength = 1, example = "User_1")
    private String username;

    @Schema(description = "Список продуктов в заказае", required = true, example = "Молоко, Хлеб")
    private List<OrderItemDto> items;

    @Schema(description = "Адрес пользователя", required = true, maxLength = 255, minLength = 25, example = "г.Москва")
    private String address;

    @Schema(description = "Телефон пользователя", required = true, maxLength = 11, minLength = 11, example = "8(921)234-45-65")
    private String phone;

    @Schema(description = "Стоимость заказа", required = true, example = "200.00")
    private BigDecimal totalPrice;
}
