package com.fitfuel.backend.mapper;

import org.springframework.stereotype.Component;

import com.fitfuel.backend.dto.request.FitnessProfileRequest;
import com.fitfuel.backend.dto.response.FitnessProfileResponse;
import com.fitfuel.backend.entity.FitnessProfile;
import com.fitfuel.backend.entity.User;

@Component
public class FitnessProfileMapper {

	 public FitnessProfile toEntity(
	            FitnessProfileRequest request,
	            User user) {

	        FitnessProfile profile = new FitnessProfile();

	        profile.setUser(user);
	        profile.setAge(request.getAge());
	        profile.setGender(request.getGender());
	        profile.setHeightCm(request.getHeightCm());
	        profile.setCurrentWeightKg(request.getCurrentWeightKg());
	        profile.setTargetWeightKg(request.getTargetWeightKg());
	        profile.setFitnessGoal(request.getFitnessGoal());
	        profile.setActivityLevel(request.getActivityLevel());

	        return profile;
	    }

	    public FitnessProfileResponse toResponse(
	            FitnessProfile profile) {

	        FitnessProfileResponse response =
	                new FitnessProfileResponse();

	        response.setId(profile.getId());
	        response.setUserId(profile.getUser().getId());
	        response.setAge(profile.getAge());
	        response.setGender(profile.getGender());
	        response.setHeightCm(profile.getHeightCm());
	        response.setCurrentWeightKg(profile.getCurrentWeightKg());
	        response.setTargetWeightKg(profile.getTargetWeightKg());
	        response.setFitnessGoal(profile.getFitnessGoal());
	        response.setActivityLevel(profile.getActivityLevel());
	        response.setCreatedAt(profile.getCreatedAt());
	        response.setUpdatedAt(profile.getUpdatedAt());

	        return response;
	    }

	    public void updateEntity(
	            FitnessProfileRequest request,
	            FitnessProfile profile) {

	        profile.setAge(request.getAge());
	        profile.setGender(request.getGender());
	        profile.setHeightCm(request.getHeightCm());
	        profile.setCurrentWeightKg(request.getCurrentWeightKg());
	        profile.setTargetWeightKg(request.getTargetWeightKg());
	        profile.setFitnessGoal(request.getFitnessGoal());
	        profile.setActivityLevel(request.getActivityLevel());
	    }

}
