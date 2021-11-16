package org.loktevik.netcracker.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "customer_paid_types",schema = "public",
            joinColumns = {@JoinColumn(name = "customer_id")},
            inverseJoinColumns = {@JoinColumn(name = "paid_type_id")}
    )
    private List<PaidType> paidTypes = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(firstName, customer.firstName)
                && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email)
                && Objects.equals(password, customer.password) && Objects.equals(phone, customer.phone)
                && Objects.equals(paidTypes, customer.paidTypes) && Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, phone, paidTypes, address);
    }
}
