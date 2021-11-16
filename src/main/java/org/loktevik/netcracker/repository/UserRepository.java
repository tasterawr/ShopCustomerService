package org.loktevik.netcracker.repository;

import org.loktevik.netcracker.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser getById(Long id);

    AppUser getByUsername(String username);
}
