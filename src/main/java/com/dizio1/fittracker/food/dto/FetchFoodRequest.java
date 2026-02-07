package com.dizio1.fittracker.food.dto;

import jakarta.validation.constraints.NotBlank;

public record FetchFoodRequest(@NotBlank String name) {
}
