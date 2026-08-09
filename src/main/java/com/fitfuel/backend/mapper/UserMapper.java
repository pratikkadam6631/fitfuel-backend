package com.fitfuel.backend.mapper;

import org.springframework.stereotype.Component;

import com.fitfuel.backend.dto.request.RegisterRequest;
import com.fitfuel.backend.dto.response.UserResponse;
import com.fitfuel.backend.entity.User;

@Component
public class UserMapper {
	
	  // DTO → Entity
    public User toEntity(RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user.setRole("CUSTOMER");

        return user;
    }


    // Entity → DTO
    public UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }

}
