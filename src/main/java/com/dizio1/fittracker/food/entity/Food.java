package com.dizio1.fittracker.food.entity;

import com.dizio1.fittracker.nutrient.entity.Nutrient;
import com.dizio1.fittracker.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String description;
    private LocalDate publishedDate;

    @OneToMany(mappedBy = "food",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Nutrient> nutrients = new HashSet<>();
}

