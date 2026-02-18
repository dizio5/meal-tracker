package com.dizio1.fittracker.foodentry.service;

import com.dizio1.fittracker.common.exception.UserNotFoundException;
import com.dizio1.fittracker.food.FoodRepository;
import com.dizio1.fittracker.food.dto.FoodItem;
import com.dizio1.fittracker.food.dto.FoodResponse;
import com.dizio1.fittracker.food.dto.mapper.FoodMapper;
import com.dizio1.fittracker.food.entity.Food;
import com.dizio1.fittracker.food.exception.FoodNotFoundException;
import com.dizio1.fittracker.food.service.FetchFoodService;
import com.dizio1.fittracker.foodentry.dto.AddFoodRequest;
import com.dizio1.fittracker.foodentry.dto.AddFoodResponse;
import com.dizio1.fittracker.foodentry.dto.mapper.FoodEntryMapper;
import com.dizio1.fittracker.foodentry.entity.FoodEntry;
import com.dizio1.fittracker.nutrient.dto.mapper.NutrientMapper;
import com.dizio1.fittracker.nutrient.entity.Nutrient;
import com.dizio1.fittracker.user.entity.User;
import com.dizio1.fittracker.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegisterEntryService {

    private final UserRepository userRepository;
    private final FoodRepository foodRepo;
    private final FetchFoodService fetchFoodService;
    private final FoodMapper foodMapper;
    private final NutrientMapper nutrientMapper;
    private final FoodEntryMapper foodEntryMapper;

    public RegisterEntryService(UserRepository userRepository,
                                FoodRepository foodRepo,
                                FetchFoodService fetchFoodService,
                                FoodMapper foodMapper,
                                NutrientMapper nutrientMapper,
                                FoodEntryMapper foodEntryMapper) {
        this.userRepository = userRepository;
        this.foodRepo = foodRepo;
        this.fetchFoodService = fetchFoodService;
        this.foodMapper = foodMapper;
        this.nutrientMapper = nutrientMapper;
        this.foodEntryMapper = foodEntryMapper;
    }

    @Transactional
    public AddFoodResponse registerFood(String username, AddFoodRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Food food = foodRepo.findByDescription(request.name())
                .orElseGet(() -> {
                    FoodItem foodItem = fetchFoodService.searchFood(request.name()).block();
                    if (foodItem == null) {
                        throw new FoodNotFoundException();
                    }

                    Food foodTemp = foodMapper.toEntity(foodItem);
                    Set<Nutrient> nutrients = foodItem.foodNutrients()
                            .stream()
                            .map(foodNutrient -> nutrientMapper.toEntity(foodNutrient, foodTemp))
                            .collect(Collectors.toSet());

                    foodTemp.getNutrients().addAll(nutrients);

                    return foodRepo.save(foodTemp);
                });

        FoodResponse foodResponse = foodMapper.toResponse(food);
        FoodEntry foodEntry = foodEntryMapper.toEntity(request.quantity(), user, food);

        user.addFoodEntry(foodEntry);
        userRepository.save(user);

        return foodEntryMapper.toAddFoodResponse(foodEntry, foodResponse);
    }
}
