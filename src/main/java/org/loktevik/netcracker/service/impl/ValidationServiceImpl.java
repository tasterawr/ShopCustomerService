package org.loktevik.netcracker.service.impl;

import org.loktevik.netcracker.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    private final String emailPattern = "^(.+)@(\\S+)$";
    private final String phonePattern = "^\\d{11}$";

    @Override
    public boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean validatePhoneNumber(String number) {
        Pattern pattern = Pattern.compile(phonePattern);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
