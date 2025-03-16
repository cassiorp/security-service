package com.cassiorp.auth.api.dto;

import lombok.Builder;

@Builder
public record AuthorizationRequestDTO(
    String token,
    String operation,
    Long userId
) {}
