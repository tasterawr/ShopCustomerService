package org.loktevik.netcracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.loktevik.netcracker.domain.Role;
import org.loktevik.netcracker.domain.AppUser;
import org.loktevik.netcracker.repository.RoleRepository;
import org.loktevik.netcracker.repository.UserRepository;
import org.loktevik.netcracker.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.getByUsername(username);

        if (user == null){
            log.error("User with username {} not found in the database.", username);
            throw new UsernameNotFoundException(String.format("User with username %s not found in the database.", username));
        } else {
            log.info("User with username {} found in the database.", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
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
        return userRepository.getByUsername(username);
    }

    @Override
    public AppUser getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public List<Role> getUserRoles(String username) {
        AppUser user = getByUsername(username);
        return user.getRoles();
    }

    @Override
    public List<Role> getUserRoles(Long id) {
        AppUser user = getById(id);
        return user.getRoles();
    }

    @Override
    public void deleteUser() {
        AppUser user = userRepository.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        userRepository.delete(user);
    }
}
