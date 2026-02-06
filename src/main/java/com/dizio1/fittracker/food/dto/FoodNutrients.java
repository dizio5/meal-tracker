package com.dizio1.fittracker.food.dto;

public record FoodNutrients(
        Long nutrientId,
        String nutrientName,
        String unitName,
        Double value
) {
}
