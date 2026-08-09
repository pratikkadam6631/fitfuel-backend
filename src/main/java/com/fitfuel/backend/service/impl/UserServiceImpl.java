package com.fitfuel.backend.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitfuel.backend.dto.response.JwtResponse;
import com.fitfuel.backend.dto.request.LoginRequest;
import com.fitfuel.backend.dto.request.RegisterRequest;
import com.fitfuel.backend.dto.response.UserResponse;
import com.fitfuel.backend.entity.User;
import com.fitfuel.backend.exception.EmailAlreadyExistsException;
import com.fitfuel.backend.exception.InvalidCredentialsException;
import com.fitfuel.backend.mapper.UserMapper;
import com.fitfuel.backend.repository.UserRepository;
import com.fitfuel.backend.security.JwtService;
import com.fitfuel.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserResponse registerUser(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(
                    "Email is already registered"
            );
        }

        User user = userMapper.toEntity(request);

        // Never save the plain-text password.
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Give a default role when the mapper does not set one.
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public JwtResponse loginUser(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception exception) {
            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        )
                );

        String token = jwtService.generateToken(user.getEmail());

        return new JwtResponse(
                token,
                "Bearer",
                user.getEmail(),
                user.getRole()
        );
    }
}