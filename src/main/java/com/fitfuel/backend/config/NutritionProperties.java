package com.fitfuel.backend.config;

import java.math.BigDecimal;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "nutrition")
public class NutritionProperties {

    private BigDecimal calorieDeficit;
    private BigDecimal calorieSurplus;

    private MacroDistribution weightLoss;
    private MacroDistribution maintenance;
    private MacroDistribution weightGain;

    @Getter
    @Setter
    public static class MacroDistribution {

        private BigDecimal proteinPerKg;
        private BigDecimal fatPerKg;
    }
}