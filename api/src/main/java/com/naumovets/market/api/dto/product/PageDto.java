package com.naumovets.market.api.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель списка продуктов")
public class PageDto<E> {

    @Schema(description = "Список продуктов",
            required = true,
            example = "{\n" +
            "      \"id\": 1,\n" +
            "      \"title\": \"Сахар\",\n" +
            "      \"cost\": 100,\n" +
            "      \"categoryTitle\": \"Food\"\n" +
            "    }")
    private List<E> items;

    @Schema(description = "Страница с продуктами", required = true, example = "1")
    private int page;

    @Schema(description = "Общее количество страниц", required = true, example = "5")
    private int totalPages;
}
