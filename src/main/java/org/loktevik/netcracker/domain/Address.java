package org.loktevik.netcracker.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="addresses")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String state;
    private String country;
}
