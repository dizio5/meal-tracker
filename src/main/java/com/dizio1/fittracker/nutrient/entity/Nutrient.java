package com.dizio1.fittracker.nutrient.entity;

import com.dizio1.fittracker.food.entity.Food;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nutrients")
public class Nutrient {

    @Id
    private Long id;
    private String name;
    private String unitName;
    private Double value;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    @JsonIgnore
    private Food food;
}
