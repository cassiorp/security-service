package com.cassiorp.auth.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserRequestDTO(
    @NotBlank
    String email,
    @NotBlank
    String password,
    @NotBlank
    String fullName
) {}