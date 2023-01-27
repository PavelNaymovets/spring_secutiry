package com.naumovets.spring_secutiry.repositories.authentication;

import com.naumovets.spring_secutiry.entities.authentication.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
