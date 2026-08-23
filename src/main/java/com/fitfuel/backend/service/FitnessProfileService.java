package com.fitfuel.backend.service;

import com.fitfuel.backend.dto.request.FitnessProfileRequest;
import com.fitfuel.backend.dto.response.FitnessProfileResponse;

public interface FitnessProfileService {

	FitnessProfileResponse createProfile(String email,FitnessProfileRequest request );

	FitnessProfileResponse getMyProfile(String email);

	 FitnessProfileResponse updateProfile( String email, FitnessProfileRequest request );
	 
	 void deleteProfile(String email);

}
