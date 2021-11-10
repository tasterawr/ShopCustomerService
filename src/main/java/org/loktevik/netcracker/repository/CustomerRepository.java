package org.loktevik.netcracker.repository;

import org.loktevik.netcracker.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer save(Customer customer);

    Customer getById(Integer id);

    List<Customer> getAll();

    void deleteById(Integer id);

    void delete(Customer customer);
}
