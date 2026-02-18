package com.dizio1.fittracker.food.controller;

import com.dizio1.fittracker.food.dto.FoodItem;
import com.dizio1.fittracker.food.dto.FetchFoodRequest;
import com.dizio1.fittracker.food.service.FetchFoodService;
import com.dizio1.fittracker.foodentry.service.FoodEntryService;
import com.dizio1.fittracker.foodentry.dto.AddFoodRequest;
import com.dizio1.fittracker.foodentry.dto.AddFoodResponse;
import com.dizio1.fittracker.foodentry.dto.FoodEntryResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/foods")
public class FoodController {

    private final FetchFoodService fetchFoodService;
    private final FoodEntryService foodEntryService;

    public FoodController(FetchFoodService fetchFoodService, FoodEntryService foodEntryService) {
        this.fetchFoodService = fetchFoodService;
        this.foodEntryService = foodEntryService;
    }

    @PostMapping("/search")
    public Mono<FoodItem> search(@Valid @RequestBody FetchFoodRequest request) {
        return fetchFoodService.searchFood(request.name());
    }

    @PostMapping("/add")
    public AddFoodResponse registerFood(@AuthenticationPrincipal Jwt jwt,
                                        @Valid @RequestBody AddFoodRequest request) {
        return foodEntryService.registerFood(jwt.getSubject(), request);
    }

    @GetMapping
    public Page<FoodEntryResponse> getAllFoodFromUser(@AuthenticationPrincipal Jwt jwt,
                                                      @PageableDefault(
                                                              size = 20,
                                                              sort = "consumedAt",
                                                              direction = Sort.Direction.DESC
                                                      ) Pageable pageable) {
        return foodEntryService.getAllFoodFromUser(jwt.getSubject(), pageable);
    }

    @GetMapping("/{date}")
    public Page<FoodEntryResponse> getAllFoodFromDate(@AuthenticationPrincipal Jwt jwt,
                                                      @PageableDefault() Pageable pageable,
                                                      @PathVariable LocalDate date) {
        return foodEntryService.getEntriesFromDate(jwt.getSubject(), date, pageable);
    }
}
