package com.dizio1.fittracker.foodentry.repository;

import com.dizio1.fittracker.foodentry.entity.FoodEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodEntryRepository extends JpaRepository<FoodEntry, Long> {

    Page<FoodEntry> findAllByUserId(Long id, Pageable pageable);
}
