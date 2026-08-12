package com.fitfuel.backend.dto.request;

import com.fitfuel.backend.enums.ActivityLevel;
import com.fitfuel.backend.enums.FitnessGoal;
import com.fitfuel.backend.enums.Gender;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FitnessProfileRequest {

	@NotNull(message = "Age is required")
    @Min(value = 13, message = "Age must be at least 13")
    @Max(value = 100, message = "Age must not exceed 100")
    private Integer age;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Height is required")
    @DecimalMin(value = "50.0", message = "Height must be at least 50 cm")
    @DecimalMax(value = "300.0", message = "Height must not exceed 300 cm")
    private Double heightCm;

    @NotNull(message = "Current weight is required")
    @DecimalMin(value = "20.0", message = "Current weight must be at least 20 kg")
    @DecimalMax(value = "500.0", message = "Current weight must not exceed 500 kg")
    private Double currentWeightKg;

    @NotNull(message = "Target weight is required")
    @DecimalMin(value = "20.0", message = "Target weight must be at least 20 kg")
    @DecimalMax(value = "500.0", message = "Target weight must not exceed 500 kg")
    private Double targetWeightKg;

    @NotNull(message = "Fitness goal is required")
    private FitnessGoal fitnessGoal;

    @NotNull(message = "Activity level is required")
    private ActivityLevel activityLevel;

}
