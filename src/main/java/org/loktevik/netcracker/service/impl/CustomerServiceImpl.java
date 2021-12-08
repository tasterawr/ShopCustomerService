package org.loktevik.netcracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Address;
import org.loktevik.netcracker.domain.AppUser;
import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.repository.AddressRepository;
import org.loktevik.netcracker.repository.CustomerRepository;
import org.loktevik.netcracker.repository.PaidTypeRepository;
import org.loktevik.netcracker.repository.UserRepository;
import org.loktevik.netcracker.service.CustomerService;
import org.loktevik.netcracker.service.PaidTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepo;
    private final PaidTypeRepository paidTypeRepo;
    private final UserRepository userRepo;
    private final AddressRepository addressRepo;

    @Override
    public Customer saveCustomer(Customer customer) {
        customerRepo.save(customer);
        return customer;
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        return customerRepo.findCustomerByUserUsername(username);
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
    public void updateCustomer(String parameter, String newValue) {
        Customer customer = getCustomerByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());

        switch (parameter) {
            case "firstName" -> customer.setFirstName(newValue);
            case "lastName" -> customer.setLastName(newValue);
            case "phone" -> customer.setPhone(newValue);
            case "email" -> customer.setEmail(newValue);
            case "username" -> {
                AppUser user = userRepo.getByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName());
                user.setUsername(newValue);
                break;
            }
            case "password" -> {
                AppUser user = userRepo.getByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName());
                user.setPassword(newValue);
                break;
            }
            case "address" -> {
                String[] addressParts = newValue.split("");
                Address address = customer.getAddress();
                address.setCountry(addressParts[0]);
                address.setState(addressParts[1]);
                address.setCity(addressParts[2]);
                addressRepo.save(address);
            }
            case "paidTypes" -> {
                String[] paidTypeIds = newValue.split("");
                for (String id : paidTypeIds) {
                    PaidType paidType = paidTypeRepo.getById(Long.parseLong(id));
                    if (!customer.getPaidTypes().contains(paidType)) {
                        customer.getPaidTypes().add(paidType);
                    }
                }
            }
        }

        customerRepo.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }

    @Override
    public void delete(Customer customer) {
        AppUser user = customer.getUser();
        customerRepo.delete(customer);
        userRepo.delete(user);
    }
}
