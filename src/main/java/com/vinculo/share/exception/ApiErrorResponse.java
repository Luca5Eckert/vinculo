package com.vinculo.share.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {}