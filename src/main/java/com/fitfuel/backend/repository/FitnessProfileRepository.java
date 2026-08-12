package com.fitfuel.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitfuel.backend.entity.FitnessProfile;

public interface FitnessProfileRepository extends JpaRepository<FitnessProfile,  Long> {

	Optional<FitnessProfile> findByUser_Id(Long userId);

    boolean existsByUser_Id(Long userId);

}
