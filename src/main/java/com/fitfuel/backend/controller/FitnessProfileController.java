package com.fitfuel.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitfuel.backend.dto.request.FitnessProfileRequest;
import com.fitfuel.backend.dto.response.FitnessProfileResponse;
import com.fitfuel.backend.service.FitnessProfileService;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fitness-profile")
public class FitnessProfileController {

	    private final FitnessProfileService fitnessProfileService;

	    public FitnessProfileController(
	            FitnessProfileService fitnessProfileService) {

	        this.fitnessProfileService = fitnessProfileService;
	    }

	    @PostMapping
	    public ResponseEntity<FitnessProfileResponse> createProfile(
	            @Valid @RequestBody FitnessProfileRequest request,
	            Authentication authentication) {

	        String email = authentication.getName();

	        FitnessProfileResponse response =
	                fitnessProfileService.createProfile(email, request);

	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(response);
	        }
	    
	    @GetMapping("/me")
	    public ResponseEntity<FitnessProfileResponse> getMyProfile(
	            Authentication authentication) {

	        String email = authentication.getName();

	        FitnessProfileResponse response =
	                fitnessProfileService.getMyProfile(email);

	        return ResponseEntity.ok(response);
	    }
	    
	    @PutMapping("/me")
	    public ResponseEntity<FitnessProfileResponse> updateMyProfile(
	            @Valid @RequestBody FitnessProfileRequest request,
	            Authentication authentication) {

	        String email = authentication.getName();

	        FitnessProfileResponse response =
	                fitnessProfileService.updateProfile(email, request);

	        return ResponseEntity.ok(response);
	        
	    }
	    
	    @DeleteMapping("/me")
	    public ResponseEntity<Void> deleteMyProfile(
	            Authentication authentication) {

	        String email = authentication.getName();

	        fitnessProfileService.deleteProfile(email);

	        return ResponseEntity.noContent().build();
	    }

		}