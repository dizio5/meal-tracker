package com.dizio1.fittracker.food.dto;

import java.util.List;

public record FoodSearchResponse(
        Integer totalHits,
        List<FoodItem> foods
) {
}
