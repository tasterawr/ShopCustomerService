package org.loktevik.netcracker.service;

public interface ValidationService {

    boolean validateEmail(String email);

    boolean validatePhoneNumber(String number);

}
