package org.loktevik.netcracker.rest;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Address;
import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.service.AddressService;
import org.loktevik.netcracker.service.PaidTypeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("addresses")
public class AddressController {
    private final AddressService addressService;
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> getAddress(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Address address = addressService.getById(id);
        if (address == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Address>> getAll(){
        List<Address> addresses = addressService.getAll();
        if (addresses.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> saveAddress(@RequestBody Address address){
        HttpHeaders headers = new HttpHeaders();

        if (address == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        addressService.saveAddress(address);
        return new ResponseEntity<>(address, headers, HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> updateAddress(@RequestBody Address address){
        HttpHeaders headers = new HttpHeaders();

        if (address == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        addressService.updateAddress(address);
        return new ResponseEntity<>(address, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> deleteAddress(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Address address = addressService.getById(id);
        if (address == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        addressService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("data")
    public ResponseEntity<Address> setData(){
        Address address = new Address();
        address.setCity("Саратов");
        address.setState("Сар обл");
        address.setCountry("Россия");
        addressService.saveAddress(address);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
}
