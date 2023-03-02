package com.naumovets.market.core.controller;

import com.naumovets.market.api.dto.user.UserDto;
import com.naumovets.market.core.converters.UserConverter;
import com.naumovets.market.core.service.authentication.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
@CrossOrigin("*")
public class AdminController {
    private UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll().stream().map(UserConverter::entityToDto).collect(Collectors.toList());
    }

}
