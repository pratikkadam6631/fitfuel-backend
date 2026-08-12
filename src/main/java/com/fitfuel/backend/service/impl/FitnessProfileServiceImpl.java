package com.fitfuel.backend.service.impl;

import org.springframework.stereotype.Service;

import com.fitfuel.backend.dto.request.FitnessProfileRequest;
import com.fitfuel.backend.dto.response.FitnessProfileResponse;
import com.fitfuel.backend.entity.FitnessProfile;
import com.fitfuel.backend.entity.User;
import com.fitfuel.backend.exception.FitnessProfileAlreadyExistsException;
import com.fitfuel.backend.exception.ResourceNotFoundException;
import com.fitfuel.backend.mapper.FitnessProfileMapper;
import com.fitfuel.backend.repository.FitnessProfileRepository;
import com.fitfuel.backend.repository.UserRepository;
import com.fitfuel.backend.service.FitnessProfileService;

@Service
public class FitnessProfileServiceImpl implements FitnessProfileService {

	    private final FitnessProfileRepository fitnessProfileRepository;
	    private final UserRepository userRepository;
	    private final FitnessProfileMapper fitnessProfileMapper;

	    public FitnessProfileServiceImpl(
	            FitnessProfileRepository fitnessProfileRepository,
	            UserRepository userRepository,
	            FitnessProfileMapper fitnessProfileMapper) {

	        this.fitnessProfileRepository = fitnessProfileRepository;
	        this.userRepository = userRepository;
	        this.fitnessProfileMapper = fitnessProfileMapper;
	    }

	    @Override
	    public FitnessProfileResponse createProfile(
	            String email,
	            FitnessProfileRequest request) {

	        User user = userRepository.findByEmail(email)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "User not found with email: " + email
	                        )
	                );

	        if (fitnessProfileRepository.existsByUser_Id(user.getId())) {
		throw new FitnessProfileAlreadyExistsException(
		        "Fitness profile already exists for this user"
		);
	        }

	        FitnessProfile fitnessProfile =
	                fitnessProfileMapper.toEntity(request, user);

	        FitnessProfile savedProfile =
	                fitnessProfileRepository.save(fitnessProfile);

	        return fitnessProfileMapper.toResponse(savedProfile);
	    }

	    @Override
	    public FitnessProfileResponse getMyProfile(String email) {

	        throw new UnsupportedOperationException(
	                "Get profile is not implemented yet"
	        );
	    }

	    @Override
	    public FitnessProfileResponse updateProfile( String email, FitnessProfileRequest request) {

	        throw new UnsupportedOperationException(
	                "Update profile is not implemented yet"
	        );
	    }



}
