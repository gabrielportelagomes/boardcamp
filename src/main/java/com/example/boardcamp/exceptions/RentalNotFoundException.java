package com.example.boardcamp.exceptions;

import org.springframework.http.HttpStatus;

public class RentalNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public RentalNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
