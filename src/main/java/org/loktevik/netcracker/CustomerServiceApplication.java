package org.loktevik.netcracker;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.AppUser;
import org.loktevik.netcracker.domain.Role;
import org.loktevik.netcracker.service.UserService;
import org.loktevik.netcracker.service.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication(scanBasePackages = "org.loktevik.netcracker")
public class CustomerServiceApplication{
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
