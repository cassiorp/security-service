package com.cassiorp.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenException extends ResponseStatusException {
  public ForbiddenException(String reason) {
    super(HttpStatus.FORBIDDEN, reason);
  }
}
