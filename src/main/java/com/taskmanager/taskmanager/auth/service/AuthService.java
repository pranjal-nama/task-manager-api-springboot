package com.taskmanager.taskmanager.auth.service;

import com.taskmanager.taskmanager.auth.dto.AuthResponse;
import com.taskmanager.taskmanager.auth.dto.RegisterRequest;

public interface AuthService {
	AuthResponse register(RegisterRequest request);
}
