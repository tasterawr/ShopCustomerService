package org.loktevik.netcracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Role;
import org.loktevik.netcracker.domain.AppUser;
import org.loktevik.netcracker.repository.RoleRepository;
import org.loktevik.netcracker.repository.UserRepository;
import org.loktevik.netcracker.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = userRepository.getByUsername(username);
        Role role = roleRepository.getByName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public void addRoleToUser(Long id, String roleName) {
        AppUser appUser = userRepository.getById(id);
        Role role = roleRepository.getByName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public AppUser getByUsername(String username) {
        return null;
    }

    @Override
    public AppUser getById(Long id) {
        return null;
    }

    @Override
    public List<Role> getUserRoles(String username) {
        return null;
    }

    @Override
    public List<Role> getUserRoles(Long id) {
        return null;
    }
}
