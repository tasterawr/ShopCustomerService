package org.loktevik.netcracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.repository.CustomerRepository;
import org.loktevik.netcracker.repository.PaidTypeRepository;
import org.loktevik.netcracker.service.PaidTypeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaidTypeServiceImpl implements PaidTypeService {
    private final PaidTypeRepository paidTypeRepo;
    private final CustomerRepository customerRepo;

    @Override
    public PaidType save(PaidType paidType) {
        return paidTypeRepo.save(paidType);
    }

    @Override
    public PaidType getById(Long id) {
        return paidTypeRepo.getById(id);
    }

    @Override
    public PaidType getByName(String name) {
        return paidTypeRepo.findByName(name);
    }

    @Override
    public List<PaidType> findByCustomers_Id(Long id) {
        return paidTypeRepo.findByCustomers_Id(id);
    }

    @Override
    public Long[] getCustomerPaidtypes() {
        Customer customer = customerRepo.findCustomerByUserUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        return customer.getPaidTypes().stream()
                .map(PaidType::getId)
                .toArray(Long[]::new);
    }

    @Override
    public List<PaidType> getAllPaidTypes() {
        return paidTypeRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        paidTypeRepo.deleteById(id);
    }
}
