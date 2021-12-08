package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;

import java.util.List;

public interface CustomerService {
    Customer getCustomerByUsername(String username);

    Customer saveCustomer(Customer customer);

    Customer getById(Long id);

    List<Customer> getAllCustomers();

    void updateCustomer(String parameter, String newValue);

    void deleteById(Long id);

    void delete(Customer customer);
}
