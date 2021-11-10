package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);

    Customer getById(Integer id);

    List<Customer> getAllCustomers();

    void deleteById(Integer id);

    void delete(Customer customer);
}
