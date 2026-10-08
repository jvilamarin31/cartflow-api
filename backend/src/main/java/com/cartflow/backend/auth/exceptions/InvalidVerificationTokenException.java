package com.cartflow.backend.auth.exceptions;

import com.cartflow.backend.common.exceptions.CartflowException;
import org.springframework.http.HttpStatus;

public class InvalidVerificationTokenException extends CartflowException {
    public InvalidVerificationTokenException() {
        super("El enlace de verificación es inválido o ya fue utilizado", HttpStatus.NOT_FOUND);
    }
}
