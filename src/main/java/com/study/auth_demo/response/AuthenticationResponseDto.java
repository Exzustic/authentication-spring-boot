package com.study.auth_demo.response;

import java.util.UUID;

public record AuthenticationResponseDto(
        String accessToken,
        UUID refreshToken
) {
}
