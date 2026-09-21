package com.cartflow.backend.auth.exceptions;

import com.cartflow.backend.common.exceptions.CartflowException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends CartflowException {
    public InvalidCredentialsException() {
        super("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
    }
}
