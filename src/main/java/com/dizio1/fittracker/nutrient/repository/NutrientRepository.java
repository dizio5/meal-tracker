package com.dizio1.fittracker.nutrient.repository;

import com.dizio1.fittracker.nutrient.entity.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NutrientRepository extends JpaRepository<Nutrient, Long> {

    List<Nutrient> getAllByFoodId(Long id);
}
