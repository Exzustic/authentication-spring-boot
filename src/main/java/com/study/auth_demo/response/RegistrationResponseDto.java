package com.study.auth_demo.response;

public record RegistrationResponseDto(
        String username,
        String email,
        boolean emailVerificationRequired
) {
}
