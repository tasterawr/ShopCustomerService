package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);

    Customer getById(Long id);

    List<Customer> getAllCustomers();

    void updateCustomer(Customer customer);

    void deleteById(Long id);

    void delete(Customer customer);
}
