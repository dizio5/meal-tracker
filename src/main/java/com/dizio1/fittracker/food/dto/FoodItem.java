package com.dizio1.fittracker.food.dto;

import java.util.Date;
import java.util.List;

public record FoodItem(
        Long fdcId,
        String description,
        Date publishedDate,
        List<FoodNutrients> foodNutrients
) {
}
