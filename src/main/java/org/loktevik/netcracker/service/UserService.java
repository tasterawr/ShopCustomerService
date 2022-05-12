package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Role;
import org.loktevik.netcracker.domain.AppUser;

import java.util.List;

/**
 * Service interface for AppUser model.
 */
public interface UserService {

    /**
     * Saves new user into database and encrypts its password.
     * @param appUser new user to save.
     * @return saved user.
     */
    AppUser saveUser(AppUser appUser);

    /**
     * Saves new role into database.
     * @param role new role.
     * @return saved role.
     */
    Role saveRole(Role role);

    /**
     * Adds role with specified name to user with specified username.
     * @param username username of user to add role to.
     * @param roleName name of role to add.
     */
    void addRoleToUser(String username, String roleName);

    /**
     * Adds role with specified name to user with specified id.
     * @param id id of user to add role to.
     * @param roleName name of role to add.
     */
    void addRoleToUser(Long id, String roleName);

    /**
     * Finds all users in database.
     * @return all users from database.
     */
    List<AppUser> getUsers();

    /**
     * Finds user by specified username.
     * @param username username of user.
     * @return user with specified username.
     */
    AppUser getByUsername(String username);

    /**
     * Finds user by specified id.
     * @param id id of user.
     * @return user with specified id.
     */
    AppUser getById(Long id);

    /**
     * Finds all roles of user with specified username.
     * @param username username of user.
     * @return List of roles of user.
     */
    List<Role> getUserRoles(String username);

    /**
     * Finds all roles of user with specified id.
     * @param id id of user.
     * @return List of roles of user.
     */
    List<Role> getUserRoles(Long id);

    /**
     * Deletes current user.
     */
    void deleteUser();

    /**
     * Deletes user with specified id.
     * @param id id of user.
     */
    void deleteById(Long id);
}
