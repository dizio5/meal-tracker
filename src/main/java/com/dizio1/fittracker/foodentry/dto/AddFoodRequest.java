package com.dizio1.fittracker.foodentry.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AddFoodRequest(@NotBlank String name, @Min(1) Integer quantity) {

}
