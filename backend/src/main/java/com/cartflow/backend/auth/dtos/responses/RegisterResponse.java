package com.cartflow.backend.auth.dtos.responses;

import com.cartflow.backend.user.enums.Role;

public record RegisterResponse(Long id, String email, Role role) {
}
