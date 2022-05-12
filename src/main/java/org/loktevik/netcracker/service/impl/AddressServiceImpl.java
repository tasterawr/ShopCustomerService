package org.loktevik.netcracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;
import org.loktevik.netcracker.domain.Address;
import org.loktevik.netcracker.repository.AddressRepository;
import org.loktevik.netcracker.service.AddressService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service implementation class for Address model. Implements methods from AddressService.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepo;
    private final Logger log = Logger.getLogger(AddressServiceImpl.class);

    @Override
    public Address getById(Long id) {
        log.info(new FormattedMessage("Getting address by id {}.", id));
        return addressRepo.getById(id);
    }

    @Override
    public List<Address> getAll() {
        log.info("Getting all addresses.");
        return addressRepo.findAll();
    }

    @Override
    public Address saveAddress(Address address) {
        log.info(new FormattedMessage("Saving new address (country: {}, state: {}, city: {}).",
                address.getCountry(), address.getState(), address.getCity()));
        return addressRepo.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        log.info(new FormattedMessage("Updating address with id {}.", address.getId()));
        return addressRepo.save(address);
    }

    @Override
    public void deleteById(Long id) {
        log.info(new FormattedMessage("Deleting address with id {}.", id));
        addressRepo.deleteById(id);
    }
}
