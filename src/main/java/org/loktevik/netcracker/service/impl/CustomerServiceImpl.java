package org.loktevik.netcracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;
import org.loktevik.netcracker.domain.Address;
import org.loktevik.netcracker.domain.AppUser;
import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.exceptions.InvalidDataException;
import org.loktevik.netcracker.exceptions.UniqueViolationException;
import org.loktevik.netcracker.repository.AddressRepository;
import org.loktevik.netcracker.repository.CustomerRepository;
import org.loktevik.netcracker.repository.PaidTypeRepository;
import org.loktevik.netcracker.repository.UserRepository;
import org.loktevik.netcracker.service.CustomerService;
import org.loktevik.netcracker.service.UserService;
import org.loktevik.netcracker.service.ValidationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service implementation class for Customer model. Implements methods from CustomerService.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepo;
    private final PaidTypeRepository paidTypeRepo;
    private final UserRepository userRepo;
    private final UserService userService;
    private final AddressRepository addressRepo;
    private final ValidationService validationService;
    private final Logger log = Logger.getLogger(CustomerServiceImpl.class);

    @Override
    public Customer saveCustomer(Customer customer) {
        validateCustomer(customer);
        log.info(new FormattedMessage("Saving customer with username {}.", customer.getUser().getUsername()));
        customerRepo.save(customer);
        return customer;
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        log.info(new FormattedMessage("Getting customer with username {}.", username));
        return customerRepo.findCustomerByUserUsername(username);
    }

    @Override
    public Customer getById(Long id) {
        log.info(new FormattedMessage("Getting customer with id {}.", id));
        return customerRepo.getById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        log.info("Getting all customers");
        return customerRepo.findAll();
    }

    @Override
    public void updateCustomer(String parameter, String newValue) {
        Customer customer = getCustomerByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());

        switch (parameter) {
            case "firstName" -> {
                validateCustomerName(newValue);
                customer.setFirstName(newValue);
            }
            case "lastName" -> {
                validateCustomerLastName(newValue);
                customer.setLastName(newValue);
            }
            case "phone" -> {
                validatePhone(newValue);
                customer.setPhone(newValue);
            }
            case "email" -> {
                validateEmail(newValue);
                customer.setEmail(newValue);
            }
            case "username" -> {
                validateUsername(newValue);
                AppUser user = userRepo.getByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName());
                user.setUsername(newValue);
                break;
            }
            case "password" -> {
                AppUser user = userRepo.getByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName());
                user.setPassword(newValue);
                userService.saveUser(user);
                break;
            }
            case "address" -> {
                validateAddress(newValue);
                String[] addressParts = newValue.split(" ");
                Address address = customer.getAddress();
                address.setCountry(addressParts[0]);
                address.setState(addressParts[1]);
                address.setCity(addressParts[2]);
                addressRepo.save(address);
            }
            case "paidtypes" -> {
                String[] paidTypeNames = newValue.split("-");
                customer.getPaidTypes().clear();
                for (String name : paidTypeNames) {
                    PaidType paidType = paidTypeRepo.findByName(name);
                    customer.getPaidTypes().add(paidType);
                }
            }
        }

        if (!parameter.equals("password")){
            log.info(new FormattedMessage("Updating {} for customer with username {}. New value: {}.",
                    parameter, customer.getUser().getUsername(), newValue));
        } else {
            log.info(new FormattedMessage("Updating password for customer with username {}.", customer.getUser().getUsername()));
        }

        customerRepo.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        log.info(new FormattedMessage("Deleting customer with id {}.", id));
        customerRepo.deleteById(id);
    }

    @Override
    public void delete(Customer customer) {
        AppUser user = customer.getUser();
        log.info(new FormattedMessage("Deleting customer with username {}.", user.getUsername()));
        customerRepo.delete(customer);
        userRepo.delete(user);
    }

    public void validateCustomer(Customer customer){
        validateCustomerName(customer.getFirstName());
        validateCustomerLastName(customer.getLastName());
        validateUsername(customer.getUser().getUsername());
        validateEmail(customer.getEmail());
        validatePhone(customer.getPhone());
    }

    public void validateCustomerName(String name){
        name =name.trim();
        if (name.split(" ").length > 1){
            InvalidDataException e = new InvalidDataException("Имя должно состоять из одной строки.");
            log.error(new FormattedMessage("Name {} validation exception: {}", name, e.getMessage()), e);
            throw e;
        }

        log.info(new FormattedMessage("Name {} validated successfully.", name));
    }

    public void validateCustomerLastName(String name){
        name =name.trim();
        if (name.split(" ").length > 1){
            InvalidDataException e = new InvalidDataException("Фамилия должна состоять из одной строки.");
            log.error(new FormattedMessage("Last name {} validation exception: {}", name, e.getMessage()), e);
            throw e;
        }
        log.info(new FormattedMessage("Last name {} validated successfully.", name));
    }

    public void validateEmail(String email){
        if (!validationService.validateEmail(email)){
            InvalidDataException e = new InvalidDataException("E-mail имеет неверный формат.");
            log.error(new FormattedMessage("E-mail {} validation exception: {}", email, e.getMessage()), e);
            throw e;
        }

        if (customerRepo.findCustomerByEmail(email) != null){
            UniqueViolationException e = new UniqueViolationException("Указанный E-mail уже используется.");
            log.error(new FormattedMessage("E-mail {} validation exception: {}", email, e.getMessage()), e);
            throw e;
        }

        log.info(new FormattedMessage("E-mail {} validated successfully.", email));
    }

    public void validatePhone(String phone){
        if (!validationService.validatePhoneNumber(phone)){
            InvalidDataException e = new InvalidDataException("Телефонный номер должен состоять из 11 цифр.");
            log.error(new FormattedMessage("Phone {} validation exception: {}", phone, e.getMessage()), e);
            throw e;
        }

        if (customerRepo.findCustomerByPhone(phone) != null){
            UniqueViolationException e = new UniqueViolationException("Указанный номер телефона уже используется.");
            log.error(new FormattedMessage("Phone {} validation exception: {}", phone, e.getMessage()), e);
            throw e;
        }

        log.info(new FormattedMessage("Phone {} validated successfully.", phone));
    }

    public void validateUsername(String username){
        username =username.trim();
        if (username.split(" ").length > 1){
            InvalidDataException e = new InvalidDataException("Логин должен состоять из одной строки.");
            log.error(new FormattedMessage("Username {} validation exception: {}", username, e.getMessage()), e);
            throw e;
        }

        if (customerRepo.findCustomerByUserUsername(username) != null){
            UniqueViolationException e = new UniqueViolationException("Указанный логин уже используется.");
            log.error(new FormattedMessage("Username {} validation exception: {}", username, e.getMessage()), e);
            throw e;
        }

        log.info(new FormattedMessage("Username {} validated successfully.", username));
    }

    public void validateAddress(String address){
        address = address.trim();
        if (address.split(" ").length != 3){
            InvalidDataException e = new InvalidDataException("Адрес должен состоять из 3 частей.");
            log.error(new FormattedMessage("Address {} validation exception: {}", address, e.getMessage()), e);
            throw e;
        }

        log.info(new FormattedMessage("Address {} validated successfully.", address));
    }
}
