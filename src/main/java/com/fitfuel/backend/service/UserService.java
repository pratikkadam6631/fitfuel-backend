package com.fitfuel.backend.service;

import com.fitfuel.backend.dto.request.LoginRequest;
import com.fitfuel.backend.dto.request.RegisterRequest;
import com.fitfuel.backend.dto.response.JwtResponse;
import com.fitfuel.backend.dto.response.UserResponse;

public interface UserService {
	
	UserResponse registerUser(RegisterRequest request);
	
	JwtResponse loginUser(LoginRequest request);

}
