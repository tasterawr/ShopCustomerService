package org.loktevik.netcracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UniqueViolationException extends RuntimeException{
    public UniqueViolationException(String message){
        super(message);
    }
}
