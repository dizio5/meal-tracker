package com.dizio1.fittracker.food.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "usda")
public record UsdaProperties(String baseUrl, String apiKey) {
}
