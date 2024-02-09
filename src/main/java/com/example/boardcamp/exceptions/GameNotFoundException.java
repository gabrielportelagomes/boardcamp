package com.example.boardcamp.exceptions;

import org.springframework.http.HttpStatus;

public class GameNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public GameNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
