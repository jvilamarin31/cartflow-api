package com.cartflow.backend.auth.controller;

import com.cartflow.backend.auth.dtos.requests.RegisterRequest;
import com.cartflow.backend.auth.dtos.responses.MessageResponse;
import com.cartflow.backend.auth.dtos.responses.RegisterResponse;
import com.cartflow.backend.auth.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<MessageResponse> verifyEmail(@RequestParam @NotBlank String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok(new MessageResponse("Cuenta verificada exitosamente"));
    }
}
