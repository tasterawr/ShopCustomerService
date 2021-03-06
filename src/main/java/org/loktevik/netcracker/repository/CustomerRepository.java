package org.loktevik.netcracker.repository;

import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByUserUsername(String username);

    Customer findCustomerByPhone(String phone);

    Customer findCustomerByEmail(String email);

    Customer save(Customer customer);

    Customer getById(Long id);

    List<Customer> findAll();

    void deleteById(Long id);

    void delete(Customer customer);
}
