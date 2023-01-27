package com.naumovets.spring_secutiry.converters;

import com.naumovets.spring_secutiry.dto.UserDto;
import com.naumovets.spring_secutiry.entities.authentication.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public static UserDto entityToDto(User user) {
        return new UserDto(user.getUsername(), user.getRoles());
    }
}
