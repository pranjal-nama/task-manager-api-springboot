package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.Role;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.taskmanager.taskmanager.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(User user){
		if (user.getRole() == null) {
			user.setRole(Role.USER);
		}
		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long id){
		return userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + id));
	}

	@Override
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	@Override
	public User findByEmail(String email){
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
	}
}
