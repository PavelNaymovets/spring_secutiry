package com.naumovets.spring_secutiry.converters;

import com.naumovets.spring_secutiry.dto.UserDto;
import com.naumovets.spring_secutiry.entities.authentication.Role;
import com.naumovets.spring_secutiry.entities.authentication.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public static UserDto entityToDto(User user) {
        List<String> rolesList = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        return new UserDto(user.getUsername(), rolesList);
    }
}
