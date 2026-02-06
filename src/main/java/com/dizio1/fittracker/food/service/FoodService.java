package com.dizio1.fittracker.food.service;

import com.dizio1.fittracker.common.exception.UserNotFoundException;
import com.dizio1.fittracker.food.FoodRepository;
import com.dizio1.fittracker.food.client.FoodClient;
import com.dizio1.fittracker.food.dto.FoodItem;
import com.dizio1.fittracker.food.dto.FoodNutrients;
import com.dizio1.fittracker.food.dto.FoodResponse;
import com.dizio1.fittracker.food.dto.mapper.FoodMapper;
import com.dizio1.fittracker.food.entity.Food;
import com.dizio1.fittracker.food.exception.FoodNotFoundException;
import com.dizio1.fittracker.nutrient.dto.mapper.NutrientMapper;
import com.dizio1.fittracker.nutrient.entity.Nutrient;
import com.dizio1.fittracker.nutrient.repository.NutrientRepository;
import com.dizio1.fittracker.user.entity.User;
import com.dizio1.fittracker.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private final FoodClient foodClient;
    private final FoodRepository foodRepo;
    private final UserRepository userRepo;
    private final NutrientRepository nutrientRepo;
    private final FoodMapper foodMapper;
    private final NutrientMapper nutrientMapper;

    public FoodService(FoodClient foodClient,
                       FoodRepository foodRepo,
                       UserRepository userRepo,
                       FoodMapper foodMapper,
                       NutrientRepository nutrientRepo, NutrientMapper nutrientMapper) {
        this.foodClient = foodClient;
        this.foodRepo = foodRepo;
        this.userRepo = userRepo;
        this.foodMapper = foodMapper;
        this.nutrientRepo = nutrientRepo;
        this.nutrientMapper = nutrientMapper;
    }

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

    @Transactional
    public FoodResponse addFood(String username, String query) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        FoodItem foodItem = searchFood(query).block();
        if (foodItem == null) {
            throw new FoodNotFoundException();
        }

        boolean foodExists = foodRepo.existsById(foodItem.fdcId());

        Food food = foodRepo.findById(foodItem.fdcId())
                .orElseGet(() -> foodMapper.toEntity(foodItem));

        Food saved = foodRepo.save(food);

        user.addFood(saved);
        userRepo.save(user);

        if (!foodExists) {
            Set<Nutrient> nutrients = foodItem.foodNutrients()
                    .stream()
                    .map(foodNutrients -> nutrientMapper.toEntity(foodNutrients, saved))
                    .collect(Collectors.toSet());

            nutrientRepo.saveAll(nutrients);
        }

        return foodMapper.toResponse(saved);
    }

    public List<FoodResponse> getFoods(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return foodRepo.findAllByUsers_Id(user.getId())
                .stream()
                .map(foodMapper::toResponse)
                .toList();
    }

    @Transactional
    public FoodResponse removeFoodFromUser(String username, Long id) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Food food = foodRepo.findById(id)
                .orElseThrow(FoodNotFoundException::new);

        user.removeFood(food);
        userRepo.save(user); // Siempre se guarda del lado del owning side (user en este caso)

        return foodMapper.toResponse(food);
    }
}
