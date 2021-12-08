package org.loktevik.netcracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="paidtypes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"customers"})
public class PaidType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "paidTypes", fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>();
}
