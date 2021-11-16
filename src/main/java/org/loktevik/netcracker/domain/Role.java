package org.loktevik.netcracker.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
