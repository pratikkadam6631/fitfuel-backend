package com.fitfuel.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitfuel.backend.entity.NutritionPlan;

public interface NutritionPlanRepository extends JpaRepository<NutritionPlan, Long> {
	
	 Optional<NutritionPlan> findByUser_Id(Long userId);

}