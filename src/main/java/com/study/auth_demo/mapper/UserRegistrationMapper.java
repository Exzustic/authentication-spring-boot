package com.study.auth_demo.mapper;

import com.study.auth_demo.entity.User;
import com.study.auth_demo.request.RegistrationRequestDto;
import com.study.auth_demo.response.RegistrationResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {

  public User toEntity(RegistrationRequestDto registrationRequestDto) {
    final var user = new User();

    user.setEmail(registrationRequestDto.email());
    user.setUsername(registrationRequestDto.username());
    user.setPassword(registrationRequestDto.password());

    return user;
  }

  public RegistrationResponseDto toRegistrationResponseDto(final User user) {
    return  new RegistrationResponseDto(user.getEmail(), user.getUsername());
  }
}
