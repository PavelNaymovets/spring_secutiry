package com.naumovets.spring_secutiry.repositories.authentication;

import com.naumovets.spring_secutiry.entities.authentication.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
