package com.fitfuel.backend.service;

import com.fitfuel.backend.dto.response.NutritionPlanResponse;

public interface NutritionPlanService {

    NutritionPlanResponse generateNutritionPlan(String email);

    NutritionPlanResponse getMyNutritionPlan(String email);
}