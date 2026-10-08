package com.cartflow.backend.auth.exceptions;

import com.cartflow.backend.common.exceptions.CartflowException;
import org.springframework.http.HttpStatus;

public class ExpiredVerificationTokenException extends CartflowException {
    public ExpiredVerificationTokenException() {
        super("El enlace de verificación ha expirado. Solicita uno nuevo.", HttpStatus.BAD_REQUEST);
    }
}
