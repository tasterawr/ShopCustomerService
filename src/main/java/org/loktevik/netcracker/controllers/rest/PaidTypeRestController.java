package org.loktevik.netcracker.controllers.rest;

import org.loktevik.netcracker.controllers.rest.dto.PaidTypeDto;
import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.service.PaidTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("paidtypes")
public class PaidTypeRestController {
    @Autowired
    PaidTypeService paidTypeService;

    @GetMapping
    public List<PaidTypeDto> getPaidTypes(){
        System.out.println("PAIDTYPE mapping");
        List<PaidType> paidTypes =  paidTypeService.getAllPaidTypes();
        List<PaidTypeDto> paidTypeDtos = new ArrayList<>();
        paidTypes.forEach(pt ->
                paidTypeDtos.add(new PaidTypeDto(pt.getId(), pt.getName())));

        return paidTypeDtos;
    }
}

