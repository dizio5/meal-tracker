package com.dizio1.fittracker.user.entity;

import com.dizio1.fittracker.food.entity.Food;
import com.dizio1.fittracker.foodentry.entity.FoodEntry;
import com.dizio1.fittracker.userprofile.entity.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodEntry> foodEntries;

    public void addFoodEntry(FoodEntry foodEntry) {
        foodEntries.add(foodEntry);
    }
}
