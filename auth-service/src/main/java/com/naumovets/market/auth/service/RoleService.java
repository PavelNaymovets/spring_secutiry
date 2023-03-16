package com.naumovets.market.auth.service;

import com.naumovets.market.auth.entities.Role;
import com.naumovets.market.auth.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
