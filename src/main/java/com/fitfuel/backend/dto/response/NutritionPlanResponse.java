package com.fitfuel.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutritionPlanResponse {
	
	private Long id;

    private BigDecimal bmr;

    private BigDecimal tdee;

    private BigDecimal dailyCalories;

    private BigDecimal proteinGrams;

    private BigDecimal carbohydratesGrams;

    private BigDecimal fatsGrams;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
