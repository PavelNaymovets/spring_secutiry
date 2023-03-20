package com.naumovets.market.core.controller;

import com.naumovets.market.api.dto.order.OrderDto;
import com.naumovets.market.core.converters.OrderConverter;
import com.naumovets.market.core.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Контроллер заказов", description = "Содержит методы работы с заказами")
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Operation(
            summary = "Запрос на создание заказа",
            responses = {
                    @ApiResponse(
                            description = "Заказ успешно создан", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestParam String phone, @RequestParam String address) {
        orderService.createOrder(username, phone, address);
    }

    @Operation(
            summary = "Запрос списка заказов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader String username) {
        return orderService.findByUsername(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}
