package com.dizio1.fittracker.foodentry.repository;

import com.dizio1.fittracker.foodentry.entity.FoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodEntryRepository extends JpaRepository<FoodEntry, Long> {

    List<FoodEntry> findAllByUserId(Long id);
}
