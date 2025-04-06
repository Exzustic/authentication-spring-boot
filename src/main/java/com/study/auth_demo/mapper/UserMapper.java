package com.study.auth_demo.mapper;

import com.study.auth_demo.entity.User;
import com.study.auth_demo.response.UserProfileDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserProfileDto toUserProfileDto(final User user) {
    return new UserProfileDto(user.getEmail(), user.getUsername());
  }
}
