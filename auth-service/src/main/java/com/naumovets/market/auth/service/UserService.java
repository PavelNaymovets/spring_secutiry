package com.naumovets.market.auth.service;

import com.naumovets.market.api.dto.user.UserDto;
import com.naumovets.market.api.exceptions.RoleAlreadyExistsException;
import com.naumovets.market.auth.entities.Role;
import com.naumovets.market.auth.entities.User;
import com.naumovets.market.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//самописная аннотация для измерения времени работы метода. Работает, если ее поставить и перед классом и перед методом
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public void createUser(User user) {
        Role role = roleService.getRole();
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Transactional
    public void addRole(UserDto userDto) {
        Role role = roleService.getRole(userDto.getRoles());
        User user = userRepository.findByUsername(userDto.getUsername()).get();

        Optional<Role> findingUserRoles = user.getRoles().stream()
                .filter(userRole -> userRole.getName().equals("ROLE_" + userDto.getRoles().toUpperCase()))
                .findFirst();

        if (findingUserRoles.isPresent()) {
            throw new RoleAlreadyExistsException("Такая роль у этого пользователя уже есть");
        }

        user.getRoles().add(role);
    }

    @Transactional
    public void deleteRole(UserDto userDto) {
        Role role = roleService.getRole(userDto.getRoles());
        User user = userRepository.findByUsername(userDto.getUsername()).get();
        user.getRoles().remove(role);
    }


}
