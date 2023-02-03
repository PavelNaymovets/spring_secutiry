package com.naumovets.spring_secutiry.controller;

import com.naumovets.spring_secutiry.dto.ProfileDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @GetMapping
    public ProfileDto getCurrentUserInfo(Principal principal) {
        return new ProfileDto(principal.getName());
    }
}
