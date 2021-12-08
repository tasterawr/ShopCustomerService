package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.PaidType;

import java.util.List;

public interface PaidTypeService {
    PaidType save(PaidType paidType);

    PaidType getById(Long id);

    PaidType getByName(String name);

    List<PaidType> findByCustomers_Id(Long id);

    List<PaidType> getAllPaidTypes();

    Long[] getCustomerPaidtypes();

    void deleteById(Long id);
}
