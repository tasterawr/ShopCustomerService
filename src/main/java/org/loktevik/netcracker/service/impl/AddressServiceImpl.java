package org.loktevik.netcracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Address;
import org.loktevik.netcracker.repository.AddressRepository;
import org.loktevik.netcracker.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepo;

    @Override
    public Address getById(Long id) {
        return addressRepo.getById(id);
    }

    @Override
    public List<Address> getAll() {
        return addressRepo.findAll();
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public void deleteById(Long id) {
        addressRepo.deleteById(id);
    }
}
