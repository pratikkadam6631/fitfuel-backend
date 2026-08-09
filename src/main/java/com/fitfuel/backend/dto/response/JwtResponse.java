package com.fitfuel.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
	
	private String token;
	private String type;
	private String email;
	private String role;

}
