package com.naumovets.market.auth.controller;

import com.naumovets.market.api.dto.user.UserDto;
import com.naumovets.market.auth.converters.UserConverter;
import com.naumovets.market.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {
    private UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll().stream().map(UserConverter::entityToDto).collect(Collectors.toList());
    }

    @PutMapping("/role/add")
    public void addUserRole(@RequestBody UserDto userDto) {
        userService.addRole(userDto);
    }

    @PutMapping("/role/delete")
    public void deleteUserRole(@RequestBody UserDto userDto) {
        userService.deleteRole(userDto);
    }

}
