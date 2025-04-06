package com.study.auth_demo.service;

import com.study.auth_demo.entity.User;
import com.study.auth_demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User registerUser(User request) {
    if ((userRepository.existsByUsername(request.getUsername())) ||
            userRepository.existsByEmail(request.getEmail())) {

      throw new ValidationException("Username or Email is already in use");
    }

    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    return userRepository.save(user);
  }
}
