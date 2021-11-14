package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaidTypeService {
    PaidType save(PaidType paidType);

    PaidType getById(Long id);

    List<PaidType> findByCustomers_Id(Long id);

    List<PaidType> getAllPaidTypes();

    void deleteById(Long id);

    void delete(PaidType paidType);
}
