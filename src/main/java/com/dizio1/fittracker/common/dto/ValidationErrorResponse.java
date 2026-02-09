package com.dizio1.fittracker.common.dto;

import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.List;

public record ValidationErrorResponse(
        Integer status,
        String error,
        Instant timestamp,
        List<FieldError> fields
) {
    public record FieldError(String field, String message) {
    }
}
