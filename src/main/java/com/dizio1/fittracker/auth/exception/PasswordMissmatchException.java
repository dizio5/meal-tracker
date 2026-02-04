package com.dizio1.fittracker.auth.exception;

public class PasswordMissmatchException extends RuntimeException {
    public PasswordMissmatchException() {
        super("Password do not match.");
    }
}
