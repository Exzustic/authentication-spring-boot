package com.study.auth_demo.request;

public record AuthenticationRequestDto(
        String username,
        String password
) {
}
