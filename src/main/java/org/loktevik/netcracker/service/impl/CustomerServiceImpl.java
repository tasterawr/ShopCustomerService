package org.loktevik.netcracker.service.impl;

import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.repository.CustomerRepository;
import org.loktevik.netcracker.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepo;

    @Override
    public Customer saveCustomer(Customer customer) {
        customerRepo.save(customer);
        return customer;
    }

    @Override
    public Customer getById(Long id) {
        return customerRepo.getById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }

    @Override
    public void delete(Customer customer) {
        customerRepo.delete(customer);
    }
}
