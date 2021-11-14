package org.loktevik.netcracker.rest;

import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.service.PaidTypeService;
import org.loktevik.netcracker.service.PaidTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("paidTypes")
public class PaidTypeController {
    @Autowired
    PaidTypeService paidTypeService;

    @GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaidType> getPaidType(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PaidType paidType = paidTypeService.getById(id);
        if (paidType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paidType, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaidType>> getAll(){
        List<PaidType> paidTypes = paidTypeService.getAllPaidTypes();
        if (paidTypes.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paidTypes, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaidType> savePaidType(@RequestBody PaidType paidType){
        HttpHeaders headers = new HttpHeaders();

        if (paidType == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        paidTypeService.save(paidType);
        return new ResponseEntity<>(paidType, headers, HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaidType> updatePaidType(@RequestBody PaidType paidType){
        HttpHeaders headers = new HttpHeaders();

        if (paidType == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        paidTypeService.save(paidType);
        return new ResponseEntity<>(paidType, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaidType> updatePaidType(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PaidType paidType = paidTypeService.getById(id);
        if (paidType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        paidTypeService.delete(paidType);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

