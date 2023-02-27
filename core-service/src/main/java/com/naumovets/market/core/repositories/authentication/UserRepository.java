package com.naumovets.market.core.repositories.authentication;

import com.naumovets.market.core.entities.authentication.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
