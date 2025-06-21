package com.taskmanager.taskmanager.service;
import com.taskmanager.taskmanager.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
	User createUser(User user);
	User getUserById(Long id);
	List<User> getAllUsers();
	User findByEmail(String email);
}
