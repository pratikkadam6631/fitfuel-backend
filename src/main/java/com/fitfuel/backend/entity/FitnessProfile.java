package com.fitfuel.backend.entity;

import java.time.LocalDateTime;

import com.fitfuel.backend.enums.ActivityLevel;
import com.fitfuel.backend.enums.FitnessGoal;
import com.fitfuel.backend.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fitness_profiles")
@Getter
@Setter
public class FitnessProfile {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	 @OneToOne(fetch = FetchType.LAZY)
	    @JoinColumn(
	        name = "user_id",
	        nullable = false,
	        unique = true
	    )

	 private User user;

	 @Column(nullable = false)
	  private Integer age;

	 @Enumerated(EnumType.STRING)
	    @Column(nullable = false, length = 20)
	    private Gender gender;

	 @Column(name = "height_cm", nullable = false)
	    private Double heightCm;

	 @Column(name = "current_weight_kg", nullable = false)
	    private Double currentWeightKg;

	 @Column(name = "target_weight_kg", nullable = false)
	    private Double targetWeightKg;

	 @Enumerated(EnumType.STRING)
	    @Column(name = "fitness_goal", nullable = false, length = 30)
	    private FitnessGoal fitnessGoal;

	 @Enumerated(EnumType.STRING)
	    @Column(name = "activity_level", nullable = false, length = 30)
	    private ActivityLevel activityLevel;

	 @Column(name = "created_at", nullable = false)
	    private LocalDateTime createdAt;

	    @Column(name = "updated_at", nullable = false)
	    private LocalDateTime updatedAt;

	    @PrePersist
	    public void onCreate() {
	        createdAt = LocalDateTime.now();
	        updatedAt = LocalDateTime.now();
	    }

	    @PreUpdate
	    public void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }
}
