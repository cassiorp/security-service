package com.cassiorp.auth.exception;

import java.time.ZonedDateTime;

public record ApiExceptionSchema(
    String message,
    Integer status,
    String error,
    ZonedDateTime timestamp
) {}