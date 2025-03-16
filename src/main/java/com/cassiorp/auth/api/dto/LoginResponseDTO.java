package com.cassiorp.auth.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LoginResponseDTO (
    String token,
    LocalDateTime expiresAt
) {}