package org.loktevik.netcracker.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.AppUser;
import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.domain.Role;
import org.loktevik.netcracker.controllers.rest.dto.RoleToUserForm;
import org.loktevik.netcracker.service.AddressService;
import org.loktevik.netcracker.service.CustomerService;
import org.loktevik.netcracker.service.PaidTypeService;
import org.loktevik.netcracker.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminRestController {
    private final CustomerService customerService;
    private final PaidTypeService paidTypeService;
    private final AddressService addressService;
    private final UserService userService;
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value="/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/customers",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Customer customer = customerService.getById(id);
        if (customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerService.delete(customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="/paidtypes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/paidtypes",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaidType>> getAllPaidtypes(){
        List<PaidType> paidTypes = paidTypeService.getAllPaidTypes();
        if (paidTypes.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paidTypes, HttpStatus.OK);
    }

    @PostMapping(value="/paidtypes",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaidType> savePaidType(@RequestBody PaidType paidType){
        HttpHeaders headers = new HttpHeaders();

        if (paidType == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        paidTypeService.save(paidType);
        return new ResponseEntity<>(paidType, headers, HttpStatus.CREATED);
    }

    @PutMapping(value="/paidtypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaidType> updatePaidType(@RequestBody PaidType paidType){
        HttpHeaders headers = new HttpHeaders();

        if (paidType == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        paidTypeService.save(paidType);
        return new ResponseEntity<>(paidType, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/paidtypes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaidType> updatePaidType(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PaidType paidType = paidTypeService.getById(id);
        if (paidType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        paidTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("users")
    public ResponseEntity<List<AppUser>> getUsers(){
        List<AppUser> users = userService.getUsers();

        if (users.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("users/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("users/save").toUriString());
        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(uri);
        userService.saveUser(user);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("roles/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("roles/save").toUriString());
        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(uri);
        userService.saveRole(role);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("roles/add-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("users")
    public void deleteUser(){
        userService.deleteUser();
    }
}

