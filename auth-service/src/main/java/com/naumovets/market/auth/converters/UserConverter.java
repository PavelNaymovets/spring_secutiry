package com.naumovets.market.auth.converters;

import com.naumovets.market.api.dto.user.UserDto;
import com.naumovets.market.auth.entities.Role;
import com.naumovets.market.auth.entities.User;
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
