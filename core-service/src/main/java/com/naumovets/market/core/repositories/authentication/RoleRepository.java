package com.naumovets.market.core.repositories.authentication;

import com.naumovets.market.core.entities.authentication.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
