package com.cartflow.backend.auth.exceptions;

import com.cartflow.backend.common.exceptions.CartflowException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends CartflowException {
    public EmailAlreadyExistsException(String email) {
        super("El email " + email + " ya etá registrado", HttpStatus.CONFLICT);
    }
}
