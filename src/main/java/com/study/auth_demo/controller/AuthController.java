package com.study.auth_demo.controller;

import com.study.auth_demo.request.AuthenticationRequestDto;
import com.study.auth_demo.response.AuthenticationResponseDto;
import com.study.auth_demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> authenticate(
          @RequestBody final AuthenticationRequestDto authenticationRequestDto
  ) {
    return ResponseEntity.ok(
            authenticationService.authenticate(authenticationRequestDto)
    );
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<AuthenticationResponseDto> refreshToken(
          @RequestParam UUID refreshToken
  ) {
    AuthenticationResponseDto response = authenticationService
            .refreshToken(refreshToken);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> revokeToken(@RequestParam UUID refreshToken){
    authenticationService.revokeRefreshToken(refreshToken);
    return ResponseEntity.noContent().build();
  }
}
