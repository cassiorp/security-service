package com.cassiorp.auth.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponseDTO(
    String id,
    String fullName,
    String email,
    LocalDateTime createdAt
) {}
