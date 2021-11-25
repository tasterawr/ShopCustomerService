package org.loktevik.netcracker.repository;

import org.loktevik.netcracker.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address save(Address address);

    Address getById(Long id);

    List<Address> findAll();

    void deleteById(Long id);

    void delete(Address address);
}
