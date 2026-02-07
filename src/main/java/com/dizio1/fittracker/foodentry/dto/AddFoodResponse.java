package com.dizio1.fittracker.foodentry.dto;

import com.dizio1.fittracker.food.dto.FoodResponse;

import java.time.LocalDateTime;

public record AddFoodResponse(
        LocalDateTime issuedAt,
        Integer quantity,
        FoodResponse foodResponse
) {
}
