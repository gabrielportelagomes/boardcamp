package com.example.boardcamp.exceptions;

import org.springframework.http.HttpStatus;

public class CustomerConflictException extends RuntimeException {

    private final HttpStatus status;

    public CustomerConflictException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
