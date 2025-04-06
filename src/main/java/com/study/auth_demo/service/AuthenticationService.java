package com.study.auth_demo.service;

import com.study.auth_demo.entity.RefreshToken;
import com.study.auth_demo.entity.User;
import com.study.auth_demo.repository.RefreshTokenRepository;
import com.study.auth_demo.repository.UserRepository;
import com.study.auth_demo.request.AuthenticationRequestDto;
import com.study.auth_demo.response.AuthenticationResponseDto;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final long refreshTokenTtl = 60 * 60;

  public AuthenticationResponseDto authenticate(
          final AuthenticationRequestDto request
  ) {
    // Authenticate User
    final var authToken = UsernamePasswordAuthenticationToken.unauthenticated(
            request.username(),
            request.password()
    );
    final var authentication = authenticationManager.authenticate(authToken);

    // Generate JWT access token
    String accessToken = jwtService.generateToken(request.username());

    // Fetch user and create refresh token
    User user = userRepository.findByUsername(request.username()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)
    );

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUser(user);
    refreshToken.setExpiresAt(Instant.now().plusSeconds(refreshTokenTtl));
    refreshTokenRepository.save(refreshToken);

    return new AuthenticationResponseDto(accessToken, refreshToken.getId());
  }

  public AuthenticationResponseDto refreshToken(UUID refreshToken) {
    final var refreshTokenEntity = refreshTokenRepository.findByIdAndExpiresAtAfter(
                    refreshToken, Instant.now())
            .orElseThrow(
                    () -> new ResponseStatusException(BAD_REQUEST, "Invalid refresh token"));

    final var accessToken = jwtService.generateToken(refreshTokenEntity.getUser().getUsername());
    return new AuthenticationResponseDto(accessToken, refreshToken);
  }

  public void revokeRefreshToken(UUID refreshToken) {
    refreshTokenRepository.deleteById(refreshToken);
  }
}
