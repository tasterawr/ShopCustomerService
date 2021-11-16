package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Role;
import org.loktevik.netcracker.domain.AppUser;

import java.util.List;

public interface UserService {

    AppUser saveUser(AppUser appUser);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    void addRoleToUser(Long id, String roleName);

    List<AppUser> getUsers();

    AppUser getByUsername(String username);

    AppUser getById(Long id);

    List<Role> getUserRoles(String username);

    List<Role> getUserRoles(Long id);
}
