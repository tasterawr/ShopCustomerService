package org.loktevik.netcracker.controllers;

import org.loktevik.netcracker.exceptions.InvalidDataException;
import org.loktevik.netcracker.exceptions.NotFoundException;
import org.loktevik.netcracker.exceptions.UniqueViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value =  { UniqueViolationException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value =  { NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value =  { InvalidDataException.class})
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}