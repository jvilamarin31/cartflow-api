package com.cartflow.backend.auth.dtos.responses;

import com.cartflow.backend.user.enums.Role;

public record LoginResponse(String token, Long id, String name, String email, Role role) {
}
