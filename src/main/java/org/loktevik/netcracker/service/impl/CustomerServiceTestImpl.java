package org.loktevik.netcracker.service.impl;

import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceTestImpl implements CustomerService {
    private List<Customer> customers = new ArrayList<>(){
        {
            Customer customer1 = new Customer();
            customer1.setId(1L);
            customer1.setFirstName("Evgeny");
            customer1.setLastName("Bazhenov");
            customer1.setEmail("email@gmail.com");
            customer1.setPhone("88005553535");
            Customer customer2 = new Customer();
            customer2.setId(2L);
            customer2.setFirstName("Oleg");
            customer2.setLastName("Alexandrov");
            customer2.setEmail("somemail@gmail.com");
            customer2.setPhone("88005553636");
            add(customer1);
            add(customer2);
        }
    };

    @Override
    public Customer saveCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        return null;
    }

    @Override
    public Customer getById(Long id) {
        Customer customer = customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(new Customer());
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public void updateCustomer(String parameter, String newValue) {

    }

    @Override
    public void deleteById(Long id) {
        Customer customer = getById(id);
        delete(customer);
    }

    @Override
    public void delete(Customer customer) {
        customers.remove(customer);
    }
}
