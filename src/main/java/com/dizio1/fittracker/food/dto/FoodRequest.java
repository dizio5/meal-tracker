package com.dizio1.fittracker.food.dto;

import jakarta.validation.constraints.NotBlank;

public record FoodRequest(@NotBlank String name) {
}
