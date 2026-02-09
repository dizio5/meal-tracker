package com.dizio1.fittracker.common.exception;

import com.dizio1.fittracker.auth.exception.DuplicateMailException;
import com.dizio1.fittracker.auth.exception.DuplicateUsernameException;
import com.dizio1.fittracker.auth.exception.PasswordMissmatchException;
import com.dizio1.fittracker.common.dto.ErrorResponse;
import com.dizio1.fittracker.common.dto.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
        List<ValidationErrorResponse.FieldError> fields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ValidationErrorResponse.FieldError(
                        err.getField(),
                        err.getDefaultMessage()
                ))
                .toList();

        ValidationErrorResponse body = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                Instant.now(),
                fields
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(PasswordMissmatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMissmatchException(PasswordMissmatchException ex) {
        ErrorResponse  errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUsername(DuplicateUsernameException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateMailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateMailException(DuplicateMailException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}