package org.loktevik.netcracker.controllers.rest.dto;

import lombok.Data;

@Data
public class CustomerInfo {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String[] paidTypes;
    private String [] roles;
    private String[] address;
}