package com.dizio1.fittracker.food.dto.mapper;

import com.dizio1.fittracker.food.dto.FoodItem;
import com.dizio1.fittracker.food.dto.FoodResponse;
import com.dizio1.fittracker.food.entity.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    public Food toEntity(FoodItem item) {
       Food food = new Food();
       food.setDescription(item.description());
       food.setPublishedDate(item.publishedDate());
       return food;
    }

    public FoodResponse toResponse(Food food) {
        return new FoodResponse(food.getDescription(), food.getPublishedDate(), food.getNutrients());
    }
}
