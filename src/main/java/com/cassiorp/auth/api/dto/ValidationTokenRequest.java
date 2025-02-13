package com.cassiorp.auth.api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ValidationTokenRequest {
  private String token;
}
