package com.naumovets.market.api.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель продукта")
public class ProductDto {

    @Schema(description = "id продукта", required = true, example = "1")
    private Long id;

    @Schema(description = "Название продукта", required = true, maxLength = 255, minLength = 3, example = "Молоко")
    private String title;

    @Schema(description = "Цена продукта", required = true, example = "200.00")
    private BigDecimal cost;

    @Schema(description = "Категория продукта", required = true, example = "Еда")
    private String categoryTitle;
}
