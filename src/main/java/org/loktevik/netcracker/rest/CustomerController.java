package org.loktevik.netcracker.rest;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Address;
import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.service.AddressService;
import org.loktevik.netcracker.service.CustomerService;
import org.loktevik.netcracker.service.PaidTypeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import java.net.URI;
import java.net.URL;
import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService customerService;
    private final PaidTypeService paidTypeService;
    private final AddressService addressService;
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerService.getById(id);
        if (customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping(value="{id}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getOrders(@PathVariable("id") Long id){
        String uri = String.format("http://localhost:8081/orders?userId=%d", id);
        List<Order> orders = restTemplate.getForObject(uri, List.class);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getAll(){
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/paidtypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaidType>> getPaidTypes(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<PaidType> paidTypes = paidTypeService.findByCustomers_Id(id);

        if (paidTypes.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paidTypes, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        HttpHeaders headers = new HttpHeaders();

        if (customer == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        customerService.saveCustomer(customer);
        return new ResponseEntity<>(customer, headers, HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
        HttpHeaders headers = new HttpHeaders();

        if (customer == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        customerService.updateCustomer(customer);
        return new ResponseEntity<>(customer, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Customer customer = customerService.getById(id);
        if (customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("data")
    public ResponseEntity<Customer> setData(){
        Address address = addressService.getById(1L);
        Customer customer = new Customer();
        customer.setFirstName("Ilya");
        customer.setLastName("Loktev");
        customer.setEmail("abc@gmail.com");
        customer.setPhone("88005553535");
        customer.setAddress(address);

        customerService.saveCustomer(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}

class Order {
    private Long id;
    private Long offerId;
    private String name;
    private Date deliveryTime;
    private Long statusId;
    private Long customerId;
    private boolean paid;
}
