package com.dizio1.fittracker.userprofile.dto;

import jakarta.validation.constraints.Min;

public record ProfileRequest(
        @Min(5) Integer age,
        @Min(20) Double weight,
        @Min(100) Double height
) {
}
