package com.study.auth_demo.service;

import com.study.auth_demo.entity.User;
import com.study.auth_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

  private final OtpService otpService;

  private final UserRepository userRepository;

  private final JavaMailSender mailSender;

  @Async
  public void sendVerificationToken(UUID userId, String email) {
    final var token = otpService.generateAndStoreOtp(userId);

    // Localhost URL with userId and OTP token
    final var emailVerificationUrl =
            "http:://localhost:8080/api/auth/email/verify?userId=%s&token=%s"
                    .formatted(userId, token);

    final var emailText = "Please verify your email by clicking the link below:\n" + emailVerificationUrl;

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject("Email Verification");
    message.setFrom("System");
    message.setText(emailText);

    mailSender.send(message);
  }

  public void resendVerificationToken(String email) {
    User user = userRepository.findByEmail(email)
            .filter(u -> !u.isEmailVerified())
            .orElseThrow(() ->
                    new ResponseStatusException(
                            BAD_REQUEST,
                            "Email not found or already verified"
                    ));
    sendVerificationToken(user.getId(), email);
  }

  @Transactional
  public User verifyEmail(UUID userId, String token){
    if (!otpService.isOtpValid(userId, token)){
      throw new ResponseStatusException(BAD_REQUEST, "Token invalid or expired");
    }

    otpService.deleteOtp(userId);

    final var user = userRepository.findById(userId).orElseThrow(
            () -> new ResponseStatusException(BAD_REQUEST,
                    "User account has been deleted or deactivated")
    );

    if (user.isEmailVerified()) {
      throw new ResponseStatusException(BAD_REQUEST, "Email is already verified");
    }

    user.setEmailVerified(true);

    System.out.println("User "+user);
    return user;
  }
}
