package com.naumovets.market.core.converters;

import com.naumovets.market.core.entities.authentication.Role;
import com.naumovets.market.core.entities.authentication.User;
import com.naumovets.market.core.dto.UserDto;
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
