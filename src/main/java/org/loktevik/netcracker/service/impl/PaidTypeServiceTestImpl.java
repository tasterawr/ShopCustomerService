package org.loktevik.netcracker.service.impl;

import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.service.PaidTypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class PaidTypeServiceTestImpl implements PaidTypeService {
    private List<PaidType> paidTypes = new ArrayList<>(){
        {
            PaidType paidType1 = new PaidType();
            paidType1.setName("Cash");
            PaidType paidType2 = new PaidType();
            paidType2.setName("Card");
            add(paidType1);
            add(paidType2);
        }
    };

    @Override
    public PaidType save(PaidType paidType) {
        paidTypes.add(paidType);
        return paidType;
    }

    @Override
    public PaidType getById(Long id) {
        PaidType paidType = paidTypes.stream()
                .filter(pType -> pType.getId().equals(id))
                .findFirst()
                .orElse(new PaidType());
        return paidType;
    }

    @Override
    public PaidType getByName(String name) {
        return null;
    }

    @Override
    public List<PaidType> findByCustomers_Id(Long id) {
        return null;
    }

    @Override
    public List<PaidType> getAllPaidTypes() {
        return paidTypes;
    }

    @Override
    public Long[] getCustomerPaidtypesIds() {
        return new Long[0];
    }

    @Override
    public void deleteById(Long id) {
        PaidType paidType = getById(id);
        paidTypes.remove(paidType);
    }

}
