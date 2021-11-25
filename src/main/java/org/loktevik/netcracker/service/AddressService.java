package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Address;

import java.util.List;

public interface AddressService {
    Address getById(Long id);

    List<Address> getAll();

    Address saveAddress(Address address);

    Address updateAddress(Address address);

    void deleteById(Long id);
}
