package com.naumovets.market.auth.controller;

import com.naumovets.market.api.dto.profile.ProfileDto;
import com.naumovets.market.auth.entities.User;
import com.naumovets.market.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping
    public ProfileDto getCurrentUserInfo(@RequestHeader String username) {
        User user = userService.findByUsername(username).get();
        return new ProfileDto(username, user.getEmail());
    }
}
