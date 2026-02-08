package com.dizio1.fittracker.food.dto;

import com.dizio1.fittracker.nutrient.entity.Nutrient;

import java.time.LocalDate;
import java.util.Set;

public record FoodResponse(
        String description,
        LocalDate publishedDate,
        Set<Nutrient> nutrients
) {
}
