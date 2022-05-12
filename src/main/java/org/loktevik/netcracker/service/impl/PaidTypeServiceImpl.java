package org.loktevik.netcracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;
import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.repository.CustomerRepository;
import org.loktevik.netcracker.repository.PaidTypeRepository;
import org.loktevik.netcracker.service.PaidTypeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service implementation class for PaidType model. Implements methods from PaidTypeService.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PaidTypeServiceImpl implements PaidTypeService {
    private final PaidTypeRepository paidTypeRepo;
    private final CustomerRepository customerRepo;
    private final Logger log = Logger.getLogger(PaidTypeServiceImpl.class);

    @Override
    public PaidType save(PaidType paidType) {
        log.info(new FormattedMessage("Saving paidType with name {}.", paidType.getName()));
        return paidTypeRepo.save(paidType);
    }

    @Override
    public PaidType getById(Long id) {
        log.info(new FormattedMessage("Getting paidType with id {}.", id));
        return paidTypeRepo.getById(id);
    }

    @Override
    public PaidType getByName(String name) {
        log.info(new FormattedMessage("Getting paidType with name {}.", name));
        return paidTypeRepo.findByName(name);
    }

    @Override
    public List<PaidType> findByCustomers_Id(Long id) {
        log.info(new FormattedMessage("Getting paidTypes for customer with id {}.", id));
        return paidTypeRepo.findByCustomers_Id(id);
    }

    @Override
    public Long[] getCustomerPaidtypesIds() {
        Customer customer = customerRepo.findCustomerByUserUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        log.info(new FormattedMessage("Getting paidTypes' ids for customer with username {}.", customer.getUser().getUsername()));
        return customer.getPaidTypes().stream()
                .map(PaidType::getId)
                .toArray(Long[]::new);
    }

    @Override
    public List<PaidType> getAllPaidTypes() {
        log.info("Getting all paidTypes");
        return paidTypeRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        log.info(new FormattedMessage("Deleting paidType with id {}.", id));
        paidTypeRepo.deleteById(id);
    }
}
