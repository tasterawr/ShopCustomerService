package org.loktevik.netcracker;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.AppUser;
import org.loktevik.netcracker.domain.Role;
import org.loktevik.netcracker.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.saveRole(new Role(null, "USER"));
        userService.saveRole(new Role(null, "ADMIN"));

        userService.saveUser(new AppUser(null, "admin", "admin_pass", new ArrayList<>()));
        userService.addRoleToUser("admin", "ADMIN");
    }
}
