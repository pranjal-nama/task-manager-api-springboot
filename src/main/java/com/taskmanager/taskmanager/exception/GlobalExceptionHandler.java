package com.taskmanager.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle 404 - Resource Not Found
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.NOT_FOUND.value());
		error.put("error", "Resource Not Found");
		error.put("message", ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	// Handle 409 - Resource Already Exists
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<Map<String, Object>> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.CONFLICT.value());
		error.put("error", "Conflict");
		error.put("message", ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	// Handle 400 - Validation Errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, Object> errorResponse = new HashMap<>();
		Map<String, String> fieldErrors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(fieldError ->
				fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage())
		);

		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
		errorResponse.put("error", "Validation Error");
		errorResponse.put("messages", fieldErrors);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// Handle 500 - Generic Exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.put("error", "Internal Server Error");
		error.put("message", ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
