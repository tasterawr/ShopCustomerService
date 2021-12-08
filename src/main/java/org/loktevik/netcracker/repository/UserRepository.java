package org.loktevik.netcracker.repository;

import org.loktevik.netcracker.domain.AppUser;
import org.loktevik.netcracker.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser getById(Long id);

    AppUser getByUsername(String username);
}
