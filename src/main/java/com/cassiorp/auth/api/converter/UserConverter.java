package com.cassiorp.auth.api.converter;

import com.cassiorp.auth.api.dto.UserRequestDTO;
import com.cassiorp.auth.api.dto.UserResponseDTO;
import com.cassiorp.auth.entity.User;

public class UserConverter {
  private UserConverter() {
  }

  public static UserResponseDTO toDTO(User user) {
    return UserResponseDTO.builder()
        .id(user.getId())
        .email(user.getEmail())
        .fullName(user.getFullName())
        .createdAt(user.getCreatedAt())
        .build();
  }

  public static User toEntity(UserRequestDTO userRequestDTO) {
    return User.builder()
        .email(userRequestDTO.email())
        .fullName(userRequestDTO.fullName())
        .password(userRequestDTO.password())
        .build();
  }
}
