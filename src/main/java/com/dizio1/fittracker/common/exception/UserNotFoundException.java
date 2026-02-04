package com.dizio1.fittracker.common.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("User: " + message + " not found.");
    }
}
