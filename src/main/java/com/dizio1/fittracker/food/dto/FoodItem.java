package com.dizio1.fittracker.food.dto;

import java.time.LocalDate;
import java.util.List;

public record FoodItem(
        Long fdcId,
        String description,
        LocalDate publishedDate,
        List<FoodNutrients> foodNutrients
) {
}
