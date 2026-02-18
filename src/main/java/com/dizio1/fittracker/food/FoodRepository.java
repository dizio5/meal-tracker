package com.dizio1.fittracker.food;

import com.dizio1.fittracker.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("""
            SELECT f
            FROM Food f
            WHERE f.description LIKE :description
            """)
    Optional<Food> findByDescription(@Param("description") String description);
}
