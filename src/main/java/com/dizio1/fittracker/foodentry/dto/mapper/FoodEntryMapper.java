package com.dizio1.fittracker.foodentry.dto.mapper;

import com.dizio1.fittracker.food.dto.FoodResponse;
import com.dizio1.fittracker.food.dto.mapper.FoodMapper;
import com.dizio1.fittracker.food.entity.Food;
import com.dizio1.fittracker.foodentry.dto.AddFoodResponse;
import com.dizio1.fittracker.foodentry.dto.FoodEntryResponse;
import com.dizio1.fittracker.foodentry.entity.FoodEntry;
import com.dizio1.fittracker.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FoodEntryMapper {

    private final FoodMapper foodMapper;

    public FoodEntryMapper(FoodMapper foodMapper) {
        this.foodMapper = foodMapper;
    }

    public FoodEntry toEntity(Integer quantity, User user, Food food) {
        FoodEntry foodEntry = new FoodEntry();
        foodEntry.setConsumedAt(LocalDateTime.now());
        foodEntry.setQuantity(quantity);
        foodEntry.setUser(user);
        foodEntry.setFood(food);
        return foodEntry;
    }

    public AddFoodResponse toAddFoodResponse(FoodEntry foodEntry, FoodResponse foodResponse) {
        return new AddFoodResponse(foodEntry.getConsumedAt(), foodEntry.getQuantity(), foodResponse);
    }

    public FoodEntryResponse toFoodEntryResponse(FoodEntry foodEntry) {
        return new FoodEntryResponse(foodEntry.getConsumedAt(),
                foodEntry.getQuantity(),
                foodMapper.toResponse(foodEntry.getFood()));
    }
}
