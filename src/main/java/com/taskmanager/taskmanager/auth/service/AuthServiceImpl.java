package com.taskmanager.taskmanager.auth.service;

import com.taskmanager.taskmanager.auth.CustomUserDetails;
import com.taskmanager.taskmanager.auth.dto.AuthResponse;
import com.taskmanager.taskmanager.auth.dto.RegisterRequest;
import com.taskmanager.taskmanager.auth.jwt.JWTService;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.Role;
import com.taskmanager.taskmanager.exception.ResourceAlreadyExistsException;
import com.taskmanager.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final JWTService jwtService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public AuthResponse register(RegisterRequest request) {
		try {
			if (userRepository.findByEmail(request.getEmail()).isPresent()) {
				throw new ResourceAlreadyExistsException("User", "email", request.getEmail());
			}

			User user = User.builder()
					.name(request.getName())
					.email(request.getEmail())
					.password(passwordEncoder.encode(request.getPassword()))
					.role(Role.USER)
					.build();

			User savedUser = userRepository.save(user);

			String token = jwtService.generateToken(new CustomUserDetails(savedUser));

			return AuthResponse.builder()
					.message("User registered successfully!")
					.token(token)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Registration failed: " + e.getMessage());
		}
	}
}
