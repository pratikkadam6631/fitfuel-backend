package com.fitfuel.backend.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ErrorResponse {
	
	private String message;
	
	private int status;
	
//	 private LocalDateTime timestamp;
//	    private int status1;
//	    private String error;
//	   private String message1;
//	    private String path;

}
