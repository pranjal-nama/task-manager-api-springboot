package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.taskmanager.dto.TaskResponseDTO;
import com.taskmanager.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping
	public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO requestDTO) {
		return taskService.createTask(requestDTO);
	}

	@GetMapping("/user/{userId}")
	public List<TaskResponseDTO> getTasksByUserId(@PathVariable Long userId){
		return taskService.getTasksByUserId(userId);
	}

	@GetMapping("/{id}")
	public TaskResponseDTO getTaskById(@PathVariable Long id) {
		return taskService.getTaskById(id);
	}

	// Filter by Status
	@GetMapping("/status/{status}")
	public List<TaskResponseDTO> getTasksByStatus(@PathVariable String status) {
		return taskService.getTasksByStatus(status);
	}

	// Filter by Priority
	@GetMapping("/priority/{priority}")
	public List<TaskResponseDTO> getTasksByPriority(@PathVariable String priority) {
		return taskService.getTasksByPriority(priority);
	}

	// Due before a date
	@GetMapping("/due-before")
	public List<TaskResponseDTO> getTasksDueBefore(
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return taskService.getTasksDueBefore(date);
	}

	// Due between two dates
	@GetMapping("/due-between")
	public List<TaskResponseDTO> getTasksDueBetween(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
		return taskService.getTasksDueBetween(start, end);
	}

	// Search by title or description
	@GetMapping("/search")
	public List<TaskResponseDTO> searchTasks(@RequestParam("keyword") String keyword) {
		return taskService.searchTasks(keyword);
	}

	@PutMapping("/{id}")
	public TaskResponseDTO updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO requestDTO) {
		return taskService.updateTask(id, requestDTO);
	}

	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
	}
}
