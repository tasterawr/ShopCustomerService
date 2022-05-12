package org.loktevik.netcracker.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.loktevik.netcracker.controllers.rest.utils.URLProvider;
import org.loktevik.netcracker.domain.Address;
import org.loktevik.netcracker.domain.AppUser;
import org.loktevik.netcracker.domain.Customer;
import org.loktevik.netcracker.domain.PaidType;
import org.loktevik.netcracker.service.AddressService;
import org.loktevik.netcracker.service.CustomerService;
import org.loktevik.netcracker.service.PaidTypeService;
import org.loktevik.netcracker.service.UserService;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.loktevik.netcracker.controllers.rest.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("customers")
public class CustomerRestController {
    private final CustomerService customerService;
    private final PaidTypeService paidTypeService;
    private final AddressService addressService;
    private final UserService userService;
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerInfo customerInfo) throws IOException {
        addCustomer(customerInfo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerInfo getCustomer(){
        Customer customer = customerService.getCustomerByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());

        CustomerInfo info = new CustomerInfo();
        info.setId(customer.getId().toString());
        info.setFirstName(customer.getFirstName());
        info.setLastName(customer.getLastName());
        info.setEmail(customer.getEmail());
        info.setPhone(customer.getPhone());
        info.setUsername(customer.getUser().getUsername());
        info.setPassword(null);
        info.setRoles(null);
        info.setPaidTypes(customer.getPaidTypes().stream()
                .map(PaidType::getName)
                .collect(Collectors.toList())
                .toArray(new String[0]));
        info.setAddress(new String[]{
                customer.getAddress().getCountry(),
                customer.getAddress().getState(),
                customer.getAddress().getCity()
        });

        return info;
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> deleteCustomerRequest(){
        deleteCustomer();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerUpdateDto updateDto){
        HttpHeaders headers = new HttpHeaders();

        if (updateDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        customerService.updateCustomer(updateDto.getParameter(), updateDto.getNewValue());
        Customer customer = customerService.getCustomerByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        return new ResponseEntity<>(customer, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OffersResponseBody> getOffers(HttpServletRequest request) throws URISyntaxException {
        CustomerPaidTypes paidTypes = new CustomerPaidTypes();
        paidTypes.setPaidtypeIds(paidTypeService.getCustomerPaidtypesIds());
        HttpEntity<CustomerPaidTypes> nextRequest = new HttpEntity<>(paidTypes, formHeadersWithAuth(request));

        String url = URLProvider.getOfferServiceUrl() + "/offers/full-info";
        ResponseEntity<OffersResponseBody> response = restTemplate.postForEntity(url, nextRequest, OffersResponseBody.class);
        Arrays.stream(response.getBody().getOffers()).forEach(offer -> {
            offer.setPaidType(paidTypeService.getById(Long.parseLong(offer.getPaidType())).getName());
        });
        return response;
    }

    @GetMapping(value = "/offer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OfferDto getOffer(HttpServletRequest request, @PathVariable String id){
        String url = URLProvider.getOfferServiceUrl() + String.format("/offers/%s", id);
        ResponseEntity<OfferDto> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(formHeadersWithAuth(request)), OfferDto.class);
        response.getBody().setPaidType(paidTypeService.getById(Long.parseLong(response.getBody().getPaidType())).getName());

        return response.getBody();
    }

    @PostMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder(HttpServletRequest request, @RequestBody OrderDto orderInfo){
        Long customerId = customerService.getCustomerByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        JSONObject jsonObject = new JSONObject()
                .put("offerId", orderInfo.getOfferId())
                .put("amount", orderInfo.getAmount());
        HttpEntity<String> nextRequest = new HttpEntity<>(jsonObject.toString(), formHeadersWithAuth(request));

        String url = URLProvider.getOrderServiceUrl() + String.format("/orders/customer/%d/new-order", customerId);
        restTemplate.postForEntity(url, nextRequest, Object.class);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/order/{id}/delete")
    public ResponseEntity<?> deleteOrder(HttpServletRequest request, @PathVariable("id") String id){
        String url = URLProvider.getOrderServiceUrl() + String.format("/orders/%s", id);

        restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(formHeadersWithAuth(request)), OrderDto.class);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="{id}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerOrdersDto> getOrders(HttpServletRequest request, @PathVariable("id") String id){
        String url = URLProvider.getOrderServiceUrl() + String.format("/orders/customer/%s", id);

        return restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(formHeadersWithAuth(request)), CustomerOrdersDto.class
        );
    }

    @GetMapping(value="/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> getOrder(HttpServletRequest request, @PathVariable("id") String id){
        String url = URLProvider.getOrderServiceUrl() + String.format("/orders/%s", id);


        return restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(formHeadersWithAuth(request)), OrderDto.class
        );
    }

    @PostMapping(value="/order/{id}/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> setOrderPaid(HttpServletRequest request, @PathVariable("id") String id){
        String url = URLProvider.getOrderServiceUrl() + String.format("/orders/%s/pay", id);

        return restTemplate.postForEntity(url, new HttpEntity<>(formHeadersWithAuth(request)), OrderDto.class);
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

    public HttpHeaders formHeadersWithAuth(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(request.getHeader("Authorization").substring("Bearer ".length()));
        return headers;
    }

    @Transactional
    public void addCustomer(CustomerInfo info){
        AppUser appUser = new AppUser(null, info.getUsername(), info.getPassword(), new ArrayList<>());
        userService.saveUser(appUser);

        Arrays.stream(info.getRoles())
                .forEach(role -> userService.addRoleToUser(appUser.getUsername(), role));

        List<PaidType> paidTypes = new ArrayList<>();
        Arrays.stream(info.getPaidTypes())
                .forEach(paidTypeName -> {
                    paidTypes.add(paidTypeService.getByName(paidTypeName));
                });

        Address address = new Address(null, info.getAddress()[0], info.getAddress()[1], info.getAddress()[2]);
        addressService.saveAddress(address);

        Customer customer = new Customer(null,
                info.getFirstName(),
                info.getLastName(),
                info.getEmail(),
                info.getPhone(),
                appUser,
                paidTypes,
                address);

        customer.getPaidTypes().forEach(pt -> {
            pt.getCustomers().add(customer);
            paidTypeService.save(pt);
        });

        customerService.saveCustomer(customer);
    }

    public void deleteCustomer(){
        Customer customer = customerService.getCustomerByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        customerService.delete(customer);
    }
}