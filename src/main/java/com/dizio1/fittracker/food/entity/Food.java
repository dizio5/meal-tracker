package com.dizio1.fittracker.food.entity;

import com.dizio1.fittracker.nutrient.entity.Nutrient;
import com.dizio1.fittracker.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foods")
public class Food {

    @Id
    private Long id;
    private String description;
    private Date publishedDate;

    @ManyToMany(mappedBy = "foods")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Nutrient> nutrients = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
    }
}

