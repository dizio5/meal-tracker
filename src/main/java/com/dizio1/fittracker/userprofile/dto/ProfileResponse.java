package com.dizio1.fittracker.userprofile.dto;

public record ProfileResponse(
        String username,
        Integer age,
        Double weight,
        Double height
) {
}
