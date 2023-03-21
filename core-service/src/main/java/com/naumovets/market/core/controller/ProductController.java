package com.naumovets.market.core.controller;

import com.naumovets.market.api.dto.category.CategoryDto;
import com.naumovets.market.api.dto.product.PageDto;
import com.naumovets.market.api.dto.product.ProductDto;
import com.naumovets.market.api.exceptions.AppError;
import com.naumovets.market.api.exceptions.ResourceNotFoundException;
import com.naumovets.market.core.converters.CategoryConverter;
import com.naumovets.market.core.converters.ProductConverter;
import com.naumovets.market.core.entities.products.Product;
import com.naumovets.market.core.service.products.CategoryService;
import com.naumovets.market.core.service.products.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@Tag(name = "Контроллер продуктов", description = "Содержит методы работы с продуктами")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;
    private CategoryConverter categoryConverter;

    @Operation(
            summary = "Запрос на получение отфильтрованного списка продуктов",
            responses = {
                    @ApiResponse(
                            description = "Продукты найдены", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PageDto.class))
                    )
            }
    )
    @GetMapping
    public PageDto<ProductDto> getAll(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "name_part", required = false) String namePart
    ) {
        System.out.println(page + " " + minPrice + " " + maxPrice + " " + namePart);
        if (page < 1) {
            page = 1;
        }

        Page<ProductDto> jpaPage = productService.findAll(minPrice, maxPrice, namePart, page).map(ProductConverter::entityToDto);

        PageDto<ProductDto> out = new PageDto<>();
        out.setPage(jpaPage.getNumber());
        out.setTotalPages(jpaPage.getTotalPages());
        out.setItems(jpaPage.getContent());

        return out;
    }

    @Operation(
            summary = "Запрос на создание нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Не удалось создать новый продукт", responseCode = "300",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addNewProduct(@RequestBody ProductDto productDto) {
        Product product = productService.addNewProduct(productDto);
        return ProductConverter.entityToDto(product);
    }

    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        return productService.findById(id).map(ProductConverter::entityToDto).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
    }

    @Operation(
            summary = "Удаление продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Продукт удален", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        productService.deleteById(id);
    }

    @Operation(
            summary = "Изменение стоимости продукта",
            responses = {
                    @ApiResponse(
                            description = "Стоимость изменена", responseCode = "200"
                    )
            }
    )
    @PutMapping
    public void changeCost(
            @RequestParam @Parameter(description = "Идентификатор продукта", required = true) Long id,
            @RequestParam @Parameter(description = "Идентификатор продукта", required = true) Integer delta) {
        productService.changeCost(id, delta);
    }

    @Operation(
            summary = "Получение списка категорий",
            responses = {
                    @ApiResponse(
                            description = "Успешный запрос", responseCode = "200"
                    )
            }
    )
    @GetMapping("/category")
    public List<CategoryDto> getCategories() {
        return categoryService.getAll().stream().map(categoryConverter::entityToDto).collect(Collectors.toList());
    }

}
