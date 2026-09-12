package com.cartflow.backend.common.exceptions;

import org.springframework.http.HttpStatus;

public abstract class CartflowException extends RuntimeException {

    private final HttpStatus status;

    protected CartflowException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}