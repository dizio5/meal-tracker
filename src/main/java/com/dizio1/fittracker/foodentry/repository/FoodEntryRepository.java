package com.dizio1.fittracker.foodentry.repository;

import com.dizio1.fittracker.foodentry.entity.FoodEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FoodEntryRepository extends JpaRepository<FoodEntry, Long> {

    @Query("""
            SELECT f
            FROM FoodEntry f
            WHERE f.user.id = :id
            """)
    Page<FoodEntry> findAllByUserId(Long id, Pageable pageable);

    @Query("""
            SELECT f
            FROM FoodEntry f
            WHERE f.user.id = :id
                AND f.consumedAt BETWEEN :start AND :end
            """)
    Page<FoodEntry> findAllByUserIdAndDate(Long id, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
