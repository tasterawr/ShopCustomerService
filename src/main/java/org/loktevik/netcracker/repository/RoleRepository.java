package org.loktevik.netcracker.repository;

import org.loktevik.netcracker.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role getById(Long id);

    Role getByName(String name);
}
