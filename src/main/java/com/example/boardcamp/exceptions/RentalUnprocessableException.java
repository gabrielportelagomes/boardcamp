package com.example.boardcamp.exceptions;

import org.springframework.http.HttpStatus;

public class RentalUnprocessableException extends RuntimeException {
    private final HttpStatus status;

    public RentalUnprocessableException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
