package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Address;

import java.util.List;

/**
 * Service interface for Address model.
 */
public interface AddressService {

    /**
     * Returns address with provided id from database.
     * @param id id of required address.
     * @return address for provided id.
     */
    Address getById(Long id);

    /**
     * Returns all addresses from database.
     * @return all addresses from database.
     */
    List<Address> getAll();

    /**
     * Saves new address or updates state of existing address.
     * @param address Instance of new or existing address.
     * @return created or updated address.
     */
    Address saveAddress(Address address);

    /**
     * Updates existing address.
     * @param address Instance of existing address.
     * @return updated address.
     */
    Address updateAddress(Address address);

    /**
     * Deletes address with specified id.
     * @param id id of address.
     */
    void deleteById(Long id);
}
