package com.dizio1.fittracker.auth.exception;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String message) {
        super("Username: " + message + " is already in use.");
    }
}
