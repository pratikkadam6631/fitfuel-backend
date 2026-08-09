package com.fitfuel.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitfuel.backend.dto.request.LoginRequest;
import com.fitfuel.backend.dto.request.RegisterRequest;
import com.fitfuel.backend.dto.response.JwtResponse;
import com.fitfuel.backend.dto.response.UserResponse;
import com.fitfuel.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser( @Valid @RequestBody RegisterRequest request) {

        UserResponse response = userService.registerUser(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginRequest request) {

        JwtResponse response = userService.loginUser(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
