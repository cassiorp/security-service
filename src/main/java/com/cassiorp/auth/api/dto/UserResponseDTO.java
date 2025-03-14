package com.cassiorp.auth.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class UserResponseDTO {
  private String id;
  private String fullName;
  private String email;
  private LocalDateTime createdAt;
}
