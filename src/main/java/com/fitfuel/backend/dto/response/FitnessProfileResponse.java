package com.fitfuel.backend.dto.response;

import java.time.LocalDateTime;

import com.fitfuel.backend.enums.ActivityLevel;
import com.fitfuel.backend.enums.FitnessGoal;
import com.fitfuel.backend.enums.Gender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FitnessProfileResponse {

	private Long id;

    private Long userId;

    private Integer age;

    private Gender gender;

    private Double heightCm;

    private Double currentWeightKg;

    private Double targetWeightKg;

    private FitnessGoal fitnessGoal;

    private ActivityLevel activityLevel;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
