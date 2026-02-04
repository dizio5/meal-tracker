package com.dizio1.fittracker.food.controller;

import com.dizio1.fittracker.food.dto.FoodItem;
import com.dizio1.fittracker.food.dto.FoodRequest;
import com.dizio1.fittracker.food.dto.FoodSearchResponse;
import com.dizio1.fittracker.food.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/search")
    public Mono<FoodItem> search(@Valid @RequestBody FoodRequest request) {
        return foodService.searchFood(request.name());
    }
}
