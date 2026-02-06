package com.dizio1.fittracker.food.controller;

import com.dizio1.fittracker.food.dto.FoodItem;
import com.dizio1.fittracker.food.dto.FoodRequest;
import com.dizio1.fittracker.food.dto.FoodResponse;
import com.dizio1.fittracker.food.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/add")
    public FoodResponse addFood(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody FoodRequest request) {
        return foodService.addFood(jwt.getSubject(), request.name());
    }

    @GetMapping
    public List<FoodResponse> getFoods(@AuthenticationPrincipal Jwt jwt) {
        return foodService.getFoods(jwt.getSubject());
    }
}
