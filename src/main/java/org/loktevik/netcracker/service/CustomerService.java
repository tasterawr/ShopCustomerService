package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;

import java.util.List;

/**
 * Service interface for Customer model.
 */
public interface CustomerService {

    /**
     * Finds customer by specified user username.
     * @param username username of customer.
     * @return customer with specified username.
     */
    Customer getCustomerByUsername(String username);

    /**
     * Saves new or updated customer into database.
     * @param customer instanceo of new or existing customer.
     * @return saved or updated customer.
     */
    Customer saveCustomer(Customer customer);

    /**
     * Finds customer with specified id.
     * @param id id of customer.
     * @return customer with specified id.
     */
    Customer getById(Long id);

    /**
     * Finds all customers id database.
     * @return all customers from database.
     */
    List<Customer> getAllCustomers();

    /**
     * Updates specified field of customer instance.
     * @param parameter name of customer's parameter to update.
     * @param newValue new value for parameter to update.
     */
    void updateCustomer(String parameter, String newValue);

    /**
     * Deletes customer with specified id.
     * @param id id of customer.
     */
    void deleteById(Long id);

    /**
     * Deletes customer by provided instance.
     * @param customer instance of customer to delete.
     */
    void delete(Customer customer);
}
