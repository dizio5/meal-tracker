package com.dizio1.fittracker.food.dto.mapper;

import com.dizio1.fittracker.food.dto.FoodItem;
import com.dizio1.fittracker.food.dto.FoodResponse;
import com.dizio1.fittracker.food.entity.Food;
import com.dizio1.fittracker.nutrient.dto.mapper.NutrientMapper;
import com.dizio1.fittracker.nutrient.entity.Nutrient;
import com.dizio1.fittracker.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class FoodMapper {

    private final NutrientMapper nutrientMapper;

    public FoodMapper(NutrientMapper nutrientMapper) {
        this.nutrientMapper = nutrientMapper;
    }

    public Food toEntity(FoodItem item, User user) {
       Food food = new Food();
       food.setId(item.fdcId());
       food.setDescription(item.description());
       food.setPublishedDate(item.publishedDate());
       food.addUser(user);
       return food;
    }

    public FoodResponse toResponse(Food food, Set<Nutrient> nutrients) {
        return new FoodResponse(food.getDescription(), food.getPublishedDate(), nutrients);
    }
}
