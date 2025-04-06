package com.study.auth_demo.controller;

import com.study.auth_demo.mapper.UserRegistrationMapper;
import com.study.auth_demo.request.RegistrationRequestDto;
import com.study.auth_demo.response.RegistrationResponseDto;
import com.study.auth_demo.service.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {

  private final UserRegistrationService userRegistrationService;

  private final UserRegistrationMapper userRegistrationMapper;

  @PostMapping("/register")
  public ResponseEntity<RegistrationResponseDto> registerUser(
          @Valid @RequestBody final RegistrationRequestDto registrationDTO
  ) {
    final var registeredUser = userRegistrationService
            .registerUser(userRegistrationMapper.toEntity(registrationDTO));

    return ResponseEntity.ok(
            userRegistrationMapper.toRegistrationResponseDto(registeredUser)
    );
  }
}
