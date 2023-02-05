package com.naumovets.spring_secutiry.dto;

import com.naumovets.spring_secutiry.entities.authentication.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private List<String> roles;
}
