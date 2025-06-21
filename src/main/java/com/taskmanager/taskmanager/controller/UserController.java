package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public User createUser(@RequestBody @Valid User user){
		return userService.createUser(user);
	}

	@GetMapping("/{id}")
	public User getUserById( @PathVariable Long id){
		return userService.getUserById(id);
	}

	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
}
