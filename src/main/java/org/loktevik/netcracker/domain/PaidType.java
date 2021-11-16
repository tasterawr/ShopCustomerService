package org.loktevik.netcracker.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="paidtypes")
@Data
public class PaidType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "paidTypes")
    private List<Customer> customers = new ArrayList<>();
}
