package com.dizio1.fittracker.nutrient.dto.mapper;

import com.dizio1.fittracker.food.dto.FoodNutrients;
import com.dizio1.fittracker.food.entity.Food;
import com.dizio1.fittracker.nutrient.entity.Nutrient;
import org.springframework.stereotype.Component;

@Component
public class NutrientMapper {

    public Nutrient toEntity(FoodNutrients foodNutrients, Food food) {
        Nutrient nutrient = new Nutrient();
        nutrient.setName(foodNutrients.nutrientName());
        nutrient.setUnitName(foodNutrients.unitName());
        nutrient.setValue(foodNutrients.value());
        nutrient.setFood(food);
        return nutrient;
    }
}
