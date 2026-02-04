package com.dizio1.fittracker.food.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UsdaProperties.class)
class PropsConfig {}