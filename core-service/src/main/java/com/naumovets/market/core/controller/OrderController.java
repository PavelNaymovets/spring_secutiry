package com.naumovets.market.core.controller;

import com.naumovets.market.core.entities.authentication.User;
import com.naumovets.market.core.service.authentication.UserService;
import com.naumovets.market.core.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User did not find"));
        orderService.createOrder(user);
    }
}
