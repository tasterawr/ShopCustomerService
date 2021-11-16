package org.loktevik.netcracker.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}
