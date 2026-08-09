package com.fitfuel.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@Email(message = "Email is required")
    @NotBlank(message = "Email cannot be blank")
    private String email;
	
    @Size(min = 6,message = "Password must be atleast 6 character")
    private String password;

}
