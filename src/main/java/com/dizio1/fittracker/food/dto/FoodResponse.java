package com.dizio1.fittracker.food.dto;

import com.dizio1.fittracker.nutrient.entity.Nutrient;

import java.util.Date;
import java.util.Set;

public record FoodResponse(
        String description,
        Date date,
        Set<Nutrient> nutrients
) {
}
