package org.loktevik.netcracker.rest;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.AppUser;
import org.loktevik.netcracker.domain.Role;
import org.loktevik.netcracker.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("users")
    public ResponseEntity<List<AppUser>> getUsers(){
        List<AppUser> users = userService.getUsers();

        if (users.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("users/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("users/save").toUriString());
        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(uri);
        userService.saveUser(user);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("roles/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("roles/save").toUriString());
        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(uri);
        userService.saveRole(role);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("roles/add-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

@Data
class RoleToUserForm{
    private String username;
    private String roleName;
}
