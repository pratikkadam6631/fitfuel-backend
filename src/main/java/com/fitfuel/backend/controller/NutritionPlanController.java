package com.fitfuel.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitfuel.backend.dto.response.NutritionPlanResponse;
import com.fitfuel.backend.service.NutritionPlanService;

@RestController
@RequestMapping("/api/nutrition-plan")
public class NutritionPlanController {

    private final NutritionPlanService nutritionPlanService;

    public NutritionPlanController(
            NutritionPlanService nutritionPlanService) {

        this.nutritionPlanService = nutritionPlanService;
    }

    @PostMapping
    public ResponseEntity<NutritionPlanResponse> generateNutritionPlan(
            Authentication authentication) {

        String email = authentication.getName();

        NutritionPlanResponse response =
                nutritionPlanService.generateNutritionPlan(email);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<NutritionPlanResponse> getMyNutritionPlan(
            Authentication authentication) {

        String email = authentication.getName();

        NutritionPlanResponse response =
                nutritionPlanService.getMyNutritionPlan(email);

        return ResponseEntity.ok(response);
    }
}