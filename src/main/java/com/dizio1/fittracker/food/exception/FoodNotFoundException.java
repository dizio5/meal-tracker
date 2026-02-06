package com.dizio1.fittracker.food.exception;

public class FoodNotFoundException extends RuntimeException {
    public FoodNotFoundException() {
        super("Food not found.");
    }
}
