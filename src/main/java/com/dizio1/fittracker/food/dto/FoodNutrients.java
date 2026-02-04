package com.dizio1.fittracker.food.dto;

public record FoodNutrients(
        Integer nutrientId,
        String nutrientName,
        String unitName,
        Double value
) {
}
