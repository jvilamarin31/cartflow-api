package com.cartflow.backend.auth.exceptions;

import com.cartflow.backend.common.exceptions.CartflowException;
import org.springframework.http.HttpStatus;

public class EmailNotVerifiedException extends CartflowException {
    public EmailNotVerifiedException() {
        super("Debes verificar tu correo electrónico antes de iniciar sesión. Revisa tu bandeja de entrada.", HttpStatus.FORBIDDEN);
    }
}
