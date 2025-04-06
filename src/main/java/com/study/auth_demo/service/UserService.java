package com.study.auth_demo.service;

import com.study.auth_demo.entity.User;
import com.study.auth_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.GONE;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User getUserByUsername(final String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(
                    GONE, "The user accouont has been deleted or inactivated"
            ));
  }
}
