package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.PaidType;

import java.util.List;

/**
 * Service interface for PaidType model.
 */
public interface PaidTypeService {

    /**
     * Saves new or updates existing paid type.
     * @param paidType new or existing paid type to save.
     * @return created or updated paid type.
     */
    PaidType save(PaidType paidType);

    /**
     * Returns paid type with provided id from database.
     * @param id id of required paid type.
     * @return paid type for provided id.
     */
    PaidType getById(Long id);

    /**
     * Returns paid type with provided name from database.
     * @param name name of required paid type.
     * @return paid type for provided name.
     */
    PaidType getByName(String name);

    /**
     * Finds all paid types with specified customer id.
     * @param id id of customer whose paid types are need to be found.
     * @return List of paid types of specified customer.
     */
    List<PaidType> findByCustomers_Id(Long id);

    /**
     * Finds all paid types in database.
     * @return all paid types from database.
     */
    List<PaidType> getAllPaidTypes();

    /**
     * Finds all ids of customer's paid types.
     * @return an array of paid type ids.
     */
    Long[] getCustomerPaidtypesIds();

    /**
     * Deletes paid type with specified id.
     * @param id id of paid type to be deleted.
     */
    void deleteById(Long id);
}
