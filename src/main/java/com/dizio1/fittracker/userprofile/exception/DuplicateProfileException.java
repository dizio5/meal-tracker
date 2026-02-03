package com.dizio1.fittracker.userprofile.exception;

public class DuplicateProfileException extends RuntimeException {
    public DuplicateProfileException(String message) {
        super("Profile is already created: " + message);
    }
}
