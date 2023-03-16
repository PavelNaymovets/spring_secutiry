package com.naumovets.market.auth.controller;

import com.naumovets.market.api.dto.jwt.JwtResponse;
import com.naumovets.market.api.dto.user.RegistrationUserDto;
import com.naumovets.market.api.exceptions.AppError;
import com.naumovets.market.auth.entities.User;
import com.naumovets.market.auth.service.UserService;
import com.naumovets.market.auth.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
@Slf4j
public class RegistrationController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if(!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }

        if(userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким именем уже существует"), HttpStatus.BAD_REQUEST);
        }

        log.info(registrationUserDto.getUsername() + "/" + registrationUserDto.getEmail() + "/" + registrationUserDto.getPassword() + "/" + registrationUserDto.getConfirmPassword());

        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));

        userService.createUser(user);

        UserDetails userDetails = userService.loadUserByUsername(registrationUserDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token, isAdmin(userDetails)));
    }

    private Boolean isAdmin(UserDetails userDetails) {
        String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(authority -> authority.equals("ROLE_ADMIN")).findFirst().orElse(null);
        return role != null;
    }
}
