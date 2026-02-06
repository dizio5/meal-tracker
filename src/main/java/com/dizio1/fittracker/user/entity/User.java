package com.dizio1.fittracker.user.entity;

import com.dizio1.fittracker.food.entity.Food;
import com.dizio1.fittracker.userprofile.entity.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
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

    @ManyToMany
    @JoinTable(
            name = "users_foods",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "food_id"})
    )
    private Set<Food> foods = new HashSet<>();

    public void addFood(Food food) {
        foods.add(food);
        food.getUsers().add(this);
    }

    public void removeFood(Food food) {
        foods.remove(food);
        food.getUsers().remove(this);
    }
}
