package com.cartflow.backend.auth.service;

import com.cartflow.backend.auth.dtos.requests.RegisterRequest;
import com.cartflow.backend.auth.dtos.responses.RegisterResponse;
import com.cartflow.backend.auth.exceptions.EmailAlreadyExistsException;
import com.cartflow.backend.common.jwt.JwtService;
import com.cartflow.backend.common.notification.EmailService;
import com.cartflow.backend.user.entity.UserEntity;
import com.cartflow.backend.user.enums.Role;
import com.cartflow.backend.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    @Value("${app.email.verification.expiration-hours}")
    private long expirationHours;
    private static final int TOKEN_BYTES = 32;

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();

    public AuthService(IUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public RegisterResponse register(RegisterRequest request) {
        Optional<UserEntity> userByEmail = userRepository.findByEmail(request.email());
        if (userByEmail.isPresent()) {
            throw new EmailAlreadyExistsException(request.email());
        }

        String verificationToken = generateVerificationToken();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(expirationHours);

        UserEntity user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .role(Role.USER)
                .emailVerified(false)
                .verificationToken(verificationToken)
                .verificationTokenExpiresAt(expiresAt)
                .build();

        UserEntity savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(savedUser, verificationToken);
        return new RegisterResponse(savedUser.getId(),savedUser.getEmail(),savedUser.getRole());
    }

    private String generateVerificationToken() {
        byte[] bytes = new byte[TOKEN_BYTES];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }


}
