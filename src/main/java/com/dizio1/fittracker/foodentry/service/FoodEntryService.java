package com.dizio1.fittracker.foodentry.service;

import com.dizio1.fittracker.common.exception.UserNotFoundException;
import com.dizio1.fittracker.foodentry.dto.FoodEntryResponse;
import com.dizio1.fittracker.foodentry.dto.mapper.FoodEntryMapper;
import com.dizio1.fittracker.foodentry.entity.FoodEntry;
import com.dizio1.fittracker.foodentry.repository.FoodEntryRepository;
import com.dizio1.fittracker.nutrient.NutrientKey;
import com.dizio1.fittracker.nutrient.dto.mapper.NutrientResponse;
import com.dizio1.fittracker.nutrient.entity.Nutrient;
import com.dizio1.fittracker.user.entity.User;
import com.dizio1.fittracker.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FoodEntryService {

    private final UserRepository userRepository;
    private final FoodEntryMapper foodEntryMapper;
    private final FoodEntryRepository foodEntryRepo;

    public FoodEntryService(UserRepository userRepository,
                            FoodEntryMapper foodEntryMapper,
                            FoodEntryRepository foodEntryRepo) {
        this.userRepository = userRepository;
        this.foodEntryMapper = foodEntryMapper;
        this.foodEntryRepo = foodEntryRepo;
    }

    public Page<FoodEntryResponse> getAllEntriesFromUser(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return foodEntryRepo.findAllByUserId(user.getId(), pageable)
                .map(foodEntryMapper::toFoodEntryResponse);
    }

    public Page<FoodEntryResponse> getEntriesFromDate(String username, LocalDate date, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atStartOfDay().plusDays(1);

        return foodEntryRepo.findAllByUserIdAndDate(user.getId(), start, end, pageable)
                .map(foodEntryMapper::toFoodEntryResponse);
    }

    public List<NutrientResponse> getNutrientsFromDate(String username, LocalDate date, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        List<FoodEntry> foodEntries = foodEntryRepo.getListByUserIdAndDate(
                user.getId(),
                date.atStartOfDay(),
                date.atStartOfDay().plusDays(1),
                pageable);

        return calculateNutrients(foodEntries);
    }

    private List<NutrientResponse> calculateNutrients(List<FoodEntry> entries) {
        Map<NutrientKey, Double> nutrients = new HashMap<>();

        for (FoodEntry entry: entries) {
            double quantity = entry.getQuantity();
            for (Nutrient nutrient: entry.getFood().getNutrients()) {
                nutrients.merge(
                        new NutrientKey(nutrient.getName(), nutrient.getUnitName()),
                        nutrient.getValue() * quantity,
                        Double::sum);
            }
        }

        return nutrients.entrySet().stream()
                .map(n -> new NutrientResponse(n.getKey().name(),
                        n.getKey().unitName(),
                        n.getValue()))
                .sorted(Comparator.comparing(NutrientResponse::name))
                .toList();
    }
}
