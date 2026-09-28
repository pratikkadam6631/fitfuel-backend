package com.fitfuel.backend.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.fitfuel.backend.config.NutritionProperties;
import com.fitfuel.backend.dto.response.NutritionPlanResponse;
import com.fitfuel.backend.entity.FitnessProfile;
import com.fitfuel.backend.entity.NutritionPlan;
import com.fitfuel.backend.entity.User;
import com.fitfuel.backend.enums.Gender;
import com.fitfuel.backend.repository.FitnessProfileRepository;
import com.fitfuel.backend.repository.NutritionPlanRepository;
import com.fitfuel.backend.repository.UserRepository;
import com.fitfuel.backend.service.NutritionPlanService;

@Service
public class NutritionPlanServiceImpl implements NutritionPlanService {

    private final FitnessProfileRepository fitnessProfileRepository;
    private final NutritionPlanRepository nutritionPlanRepository;
    private final NutritionProperties nutritionProperties;
    private final UserRepository userRepository;

    public NutritionPlanServiceImpl(
            FitnessProfileRepository fitnessProfileRepository,
            NutritionPlanRepository nutritionPlanRepository,
            NutritionProperties nutritionProperties,
            UserRepository userRepository) {

        this.fitnessProfileRepository = fitnessProfileRepository;
        this.nutritionPlanRepository = nutritionPlanRepository;
        this.nutritionProperties = nutritionProperties;
        this.userRepository = userRepository;
    }

    // =========================================================
    // BMR CALCULATION
    // =========================================================

    private double calculateBmr(FitnessProfile profile) {

        double weight = profile.getCurrentWeightKg();
        double height = profile.getHeightCm();
        int age = profile.getAge();

        if (profile.getGender() == Gender.MALE) {

            return (10 * weight)
                    + (6.25 * height)
                    - (5 * age)
                    + 5;

        } else {

            return (10 * weight)
                    + (6.25 * height)
                    - (5 * age)
                    - 161;
        }
    }

    // =========================================================
    // TDEE CALCULATION
    // =========================================================

    private double calculateTdee(FitnessProfile profile) {

        double bmr = calculateBmr(profile);

        double activityMultiplier;

        switch (profile.getActivityLevel()) {

            case SEDENTARY:
                activityMultiplier = 1.2;
                break;

            case LIGHTLY_ACTIVE:
                activityMultiplier = 1.375;
                break;

            case MODERATELY_ACTIVE:
                activityMultiplier = 1.55;
                break;

            case VERY_ACTIVE:
                activityMultiplier = 1.725;
                break;

            case EXTRA_ACTIVE:
                activityMultiplier = 1.9;
                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid activity level");
        }

        return bmr * activityMultiplier;
    }

    // =========================================================
    // TARGET CALORIE CALCULATION
    // =========================================================

    private double calculateTargetCalories(FitnessProfile profile) {

        double tdee = calculateTdee(profile);

        switch (profile.getFitnessGoal()) {

            case WEIGHT_LOSS:

                return tdee
                        - nutritionProperties
                                .getCalorieDeficit()
                                .doubleValue();

            case WEIGHT_GAIN:

                return tdee
                        + nutritionProperties
                                .getCalorieSurplus()
                                .doubleValue();

            case MAINTENANCE:

                return tdee;

            default:
                throw new IllegalArgumentException(
                        "Invalid fitness goal");
        }
    }

    // =========================================================
    // GET MACRO CONFIGURATION BASED ON GOAL
    // =========================================================

    private NutritionProperties.MacroDistribution getMacroDistribution(
            FitnessProfile profile) {

        switch (profile.getFitnessGoal()) {

            case WEIGHT_LOSS:

                return nutritionProperties.getWeightLoss();

            case MAINTENANCE:

                return nutritionProperties.getMaintenance();

            case WEIGHT_GAIN:

                return nutritionProperties.getWeightGain();

            default:
                throw new IllegalArgumentException(
                        "Invalid fitness goal");
        }
    }

    // =========================================================
    // PROTEIN CALCULATION
    // =========================================================

    private double calculateProteinGrams(
            FitnessProfile profile) {

        double weight =
                profile.getCurrentWeightKg();

        NutritionProperties.MacroDistribution macros =
                getMacroDistribution(profile);

        double proteinPerKg =
                macros.getProteinPerKg().doubleValue();

        return weight * proteinPerKg;
    }

    // =========================================================
    // FAT CALCULATION
    // =========================================================

    private double calculateFatGrams(
            FitnessProfile profile) {

        double weight =
                profile.getCurrentWeightKg();

        NutritionProperties.MacroDistribution macros =
                getMacroDistribution(profile);

        double fatPerKg =
                macros.getFatPerKg().doubleValue();

        return weight * fatPerKg;
    }

    // =========================================================
    // CARBOHYDRATE CALCULATION
    // =========================================================

    private double calculateCarbohydratesGrams(
            double dailyCalories,
            double proteinGrams,
            double fatsGrams) {

        /*
         * Protein = 4 calories per gram
         * Carbohydrates = 4 calories per gram
         * Fat = 9 calories per gram
         */

        double proteinCalories =
                proteinGrams * 4;

        double fatCalories =
                fatsGrams * 9;

        /*
         * Calculate calories remaining
         * after protein and fat.
         */

        double remainingCalories =
                dailyCalories
                - proteinCalories
                - fatCalories;

        /*
         * Prevent negative carbohydrate values.
         */

        if (remainingCalories < 0) {
            return 0;
        }

        /*
         * Remaining calories are assigned
         * to carbohydrates.
         */

        return remainingCalories / 4;
    }

    // =========================================================
    // ROUND VALUES
    // =========================================================

    private BigDecimal roundValue(double value) {

        return BigDecimal
                .valueOf(value)
                .setScale(2, RoundingMode.HALF_UP);
    }

    // =========================================================
    // GENERATE NUTRITION PLAN
    // =========================================================

    @Override
    public NutritionPlanResponse generateNutritionPlan(
            String email) {

        // -----------------------------------------------------
        // FIND USER
        // -----------------------------------------------------

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"));

        // -----------------------------------------------------
        // FIND FITNESS PROFILE
        // -----------------------------------------------------

        FitnessProfile profile =
                fitnessProfileRepository
                        .findByUser_Id(user.getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Fitness profile not found"));

        // -----------------------------------------------------
        // CALCULATE BMR
        // -----------------------------------------------------

        double bmr =
                calculateBmr(profile);

        // -----------------------------------------------------
        // CALCULATE TDEE
        // -----------------------------------------------------

        double tdee =
                calculateTdee(profile);

        // -----------------------------------------------------
        // CALCULATE DAILY CALORIES
        // -----------------------------------------------------

        double dailyCalories =
                calculateTargetCalories(profile);

        // -----------------------------------------------------
        // CALCULATE PROTEIN
        // -----------------------------------------------------

        double proteinGrams =
                calculateProteinGrams(profile);

        // -----------------------------------------------------
        // CALCULATE FAT
        // -----------------------------------------------------

        double fatsGrams =
                calculateFatGrams(profile);

        // -----------------------------------------------------
        // CALCULATE CARBOHYDRATES
        // -----------------------------------------------------

        double carbohydratesGrams =
                calculateCarbohydratesGrams(
                        dailyCalories,
                        proteinGrams,
                        fatsGrams);

        // -----------------------------------------------------
        // FIND EXISTING PLAN OR CREATE NEW PLAN
        // -----------------------------------------------------

        NutritionPlan nutritionPlan =
                nutritionPlanRepository
                        .findByUser_Id(user.getId())
                        .orElseGet(
                                NutritionPlan::new);

        // -----------------------------------------------------
        // SET VALUES
        // -----------------------------------------------------

        nutritionPlan.setUser(user);

        nutritionPlan.setBmr(
                roundValue(bmr));

        nutritionPlan.setTdee(
                roundValue(tdee));

        nutritionPlan.setDailyCalories(
                roundValue(dailyCalories));

        nutritionPlan.setProteinGrams(
                roundValue(proteinGrams));

        nutritionPlan.setCarbohydratesGrams(
                roundValue(carbohydratesGrams));

        nutritionPlan.setFatsGrams(
                roundValue(fatsGrams));

        // -----------------------------------------------------
        // SAVE PLAN
        // -----------------------------------------------------

        NutritionPlan savedPlan =
                nutritionPlanRepository
                        .save(nutritionPlan);

        // -----------------------------------------------------
        // BUILD RESPONSE
        // -----------------------------------------------------

        NutritionPlanResponse response =
                new NutritionPlanResponse();

        response.setId(
                savedPlan.getId());

        response.setBmr(
                savedPlan.getBmr());

        response.setTdee(
                savedPlan.getTdee());

        response.setDailyCalories(
                savedPlan.getDailyCalories());

        response.setProteinGrams(
                savedPlan.getProteinGrams());

        response.setCarbohydratesGrams(
                savedPlan.getCarbohydratesGrams());

        response.setFatsGrams(
                savedPlan.getFatsGrams());

        response.setCreatedAt(
                savedPlan.getCreatedAt());

        response.setUpdatedAt(
                savedPlan.getUpdatedAt());

        return response;
    }

    // =========================================================
    // GET MY NUTRITION PLAN
    // =========================================================

    @Override
    public NutritionPlanResponse getMyNutritionPlan(
            String email) {

        // -----------------------------------------------------
        // FIND USER
        // -----------------------------------------------------

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"));

        // -----------------------------------------------------
        // FIND NUTRITION PLAN
        // -----------------------------------------------------

        NutritionPlan nutritionPlan =
                nutritionPlanRepository
                        .findByUser_Id(user.getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Nutrition plan not found"));

        // -----------------------------------------------------
        // BUILD RESPONSE
        // -----------------------------------------------------

        NutritionPlanResponse response =
                new NutritionPlanResponse();

        response.setId(
                nutritionPlan.getId());

        response.setBmr(
                nutritionPlan.getBmr());

        response.setTdee(
                nutritionPlan.getTdee());

        response.setDailyCalories(
                nutritionPlan.getDailyCalories());

        response.setProteinGrams(
                nutritionPlan.getProteinGrams());

        response.setCarbohydratesGrams(
                nutritionPlan.getCarbohydratesGrams());

        response.setFatsGrams(
                nutritionPlan.getFatsGrams());

        response.setCreatedAt(
                nutritionPlan.getCreatedAt());

        response.setUpdatedAt(
                nutritionPlan.getUpdatedAt());

        return response;
    }
}