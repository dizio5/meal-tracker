package com.dizio1.fittracker.food;

import com.dizio1.fittracker.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByDescription(String description);
    boolean existsByDescription(String description);
}
