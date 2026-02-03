package com.dizio1.fittracker.auth.exception;

public class DuplicateMailException extends RuntimeException {
    public DuplicateMailException(String message) {
        super("Mail: " + message + " is already in use.");
    }
}
