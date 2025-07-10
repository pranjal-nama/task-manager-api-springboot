package com.taskmanager.taskmanager.auth.controller;

import com.taskmanager.taskmanager.auth.dto.AuthResponse;
import com.taskmanager.taskmanager.auth.dto.RegisterRequest;
import com.taskmanager.taskmanager.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class Controller {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest request){
		AuthResponse response = authService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
