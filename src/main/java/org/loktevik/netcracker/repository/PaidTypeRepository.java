package org.loktevik.netcracker.repository;

import org.loktevik.netcracker.domain.PaidType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaidTypeRepository extends CrudRepository<PaidType, Integer> {
    PaidType save(PaidType paidType);

    PaidType getById(Integer id);

    List<PaidType> getAll();

    void deleteById(Integer id);

    void delete(PaidType paidType);
}

