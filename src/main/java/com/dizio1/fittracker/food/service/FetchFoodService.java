package com.dizio1.fittracker.food.service;

import com.dizio1.fittracker.food.client.FoodClient;
import com.dizio1.fittracker.food.dto.FoodItem;
import com.dizio1.fittracker.food.dto.FoodNutrients;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class FetchFoodService {

    private final FoodClient foodClient;

    public FetchFoodService(FoodClient foodClient) {
        this.foodClient = foodClient;
    }

    @Cacheable(cacheNames = "usda-food-search", key = "#query.toLowerCase()")
    public Mono<FoodItem> searchFood(String query) {
        return foodClient.searchFood(query, 1, 1)
                .flatMap(resp -> {
                    if (resp.foods().isEmpty()) {
                        return Mono.empty();
                    }

                    FoodItem foodItem = resp.foods().getFirst();
                    List<FoodNutrients> nutrients = foodItem.foodNutrients()
                            .stream()
                            .filter(nutrient -> nutrient.value() > 0)
                            .toList();

                    FoodItem filtered = new FoodItem(
                            foodItem.fdcId(),
                            foodItem.description(),
                            foodItem.publishedDate(),
                            nutrients
                    );

                    return Mono.just(filtered);
                });
    }
}
