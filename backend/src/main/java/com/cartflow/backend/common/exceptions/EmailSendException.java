package com.cartflow.backend.common.exceptions;

import org.springframework.http.HttpStatus;

public class EmailSendException extends CartflowException {
    public EmailSendException(String message, Throwable cause) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
}
