package com.study.auth_demo.controller;

import com.study.auth_demo.mapper.UserMapper;
import com.study.auth_demo.mapper.UserRegistrationMapper;
import com.study.auth_demo.request.RegistrationRequestDto;
import com.study.auth_demo.response.RegistrationResponseDto;
import com.study.auth_demo.response.UserProfileDto;
import com.study.auth_demo.service.EmailVerificationService;
import com.study.auth_demo.service.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {

  @Value("${email-verification.required}")
  private boolean emailVerificationRequired;

  private final UserRegistrationService userRegistrationService;

  private final EmailVerificationService emailVerificationService;

  private final UserRegistrationMapper userRegistrationMapper;

  private final UserMapper userMapper;

  @PostMapping("/register")
  public ResponseEntity<RegistrationResponseDto> registerUser(
          @Valid @RequestBody final RegistrationRequestDto registrationDTO
  ) {
    final var registeredUser = userRegistrationService
            .registerUser(userRegistrationMapper.toEntity(registrationDTO));

    if (emailVerificationRequired) {
      emailVerificationService.sendVerificationToken(
              registeredUser.getId(), registeredUser.getEmail()
      );
    }

    return ResponseEntity.ok(
            userRegistrationMapper.toRegistrationResponseDto(registeredUser, emailVerificationRequired)
    );
  }

  @PostMapping("/email/resend-verification")
  public ResponseEntity<Void> resendVerificationEmail(
          @RequestParam String email) {

    emailVerificationService.resendVerificationToken(email);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/email/verify")
  public ResponseEntity<UserProfileDto> verifyEmail(
          @RequestParam("userId") String userId,
          @RequestParam("token") String token) {


    System.out.println("Work work");
    final var verifiedUser = emailVerificationService.verifyEmail(UUID.fromString(userId), token);

    return ResponseEntity.ok(userMapper.toUserProfileDto(verifiedUser));
  }
}
