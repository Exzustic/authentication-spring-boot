package com.study.auth_demo.service;

import com.study.auth_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

  @Value("${email-verification.required}")
  private boolean emailVerificationRequired;

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username).map(user -> {
      if (emailVerificationRequired && !user.isEmailVerified()) {
//        throw new EmailVerificationException("Email is not verified");
        throw new UsernameNotFoundException("Email is not verified");
      }

      return User.builder()
              .username(username)
              .password(user.getPassword())
              .build();
    }).orElseThrow(() -> new UsernameNotFoundException(
            "User with username [%s] not found".formatted(username)
    ));
  }
}
