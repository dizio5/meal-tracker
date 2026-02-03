package com.dizio1.fittracker.auth.dto;

public record AuthResponse(String token, String tokenType, Integer ExpiresIn) {
}
