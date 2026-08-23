package com.fitfuel.backend.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fitfuel.backend.exception.FitnessProfileAlreadyExistsException;

import com.fitfuel.backend.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(
	        MethodArgumentNotValidException ex) {

	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult().getFieldErrors().forEach(error ->
	        errors.put(error.getField(), error.getDefaultMessage()));

	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	 @ExceptionHandler(EmailAlreadyExistsException.class)
	    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(
	            EmailAlreadyExistsException ex) {

	        ErrorResponse error = new ErrorResponse(
	                ex.getMessage(),
	                HttpStatus.CONFLICT.value());

	        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	    }

	    @ExceptionHandler(InvalidCredentialsException.class)
	    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
	            InvalidCredentialsException ex) {

	        ErrorResponse error = new ErrorResponse(
	                ex.getMessage(),
	                HttpStatus.UNAUTHORIZED.value());

	        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	    }

	    @ExceptionHandler(ProductNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleProductNotFoundException(
	            ProductNotFoundException exception) {

	        ErrorResponse errorResponse = new ErrorResponse(
	                exception.getMessage(),
	                HttpStatus.NOT_FOUND.value()
	        );

	        return new ResponseEntity<>(
	                errorResponse,
	                HttpStatus.NOT_FOUND
	        );
	    }

	    @ExceptionHandler(FitnessProfileAlreadyExistsException.class)
	    public ResponseEntity<ErrorResponse> handleFitnessProfileAlreadyExists(
	            FitnessProfileAlreadyExistsException exception) {

	        ErrorResponse errorResponse = new ErrorResponse(
	                exception.getMessage(),
	                HttpStatus.CONFLICT.value()
	        );

	        return new ResponseEntity<>(
	                errorResponse,
	                HttpStatus.CONFLICT
	        );
	    }
	    
	    @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
	            ResourceNotFoundException exception) {

	        ErrorResponse errorResponse = new ErrorResponse(
	                exception.getMessage(),
	                HttpStatus.NOT_FOUND.value()
	        );

	        return new ResponseEntity<>(
	                errorResponse,
	                HttpStatus.NOT_FOUND
	        );
	    }

	}