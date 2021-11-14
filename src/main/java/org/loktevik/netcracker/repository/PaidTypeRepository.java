package org.loktevik.netcracker.repository;

import org.loktevik.netcracker.domain.PaidType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaidTypeRepository extends CrudRepository<PaidType, Long> {
    PaidType save(PaidType paidType);

    PaidType getById(Long id);

    List<PaidType> findByCustomers_Id(Long id);

    List<PaidType> findAll();

    void deleteById(Long id);

    void delete(PaidType paidType);
}

