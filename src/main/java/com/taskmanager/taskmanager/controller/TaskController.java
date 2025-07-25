package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.taskmanager.dto.TaskResponseDTO;
import com.taskmanager.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;

	@PostMapping
	public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO requestDTO) {
		logger.info("Received request to create task for userId={}, title={}",
				requestDTO.getUserId(), requestDTO.getTitle());
		return taskService.createTask(requestDTO);
	}

	@GetMapping("/user/{userId}")
	public List<TaskResponseDTO> getTasksByUserId(@PathVariable Long userId){
		logger.info("Fetching tasks for userId={}", userId);
		return taskService.getTasksByUserId(userId);
	}

	@GetMapping("/{id}")
	public TaskResponseDTO getTaskById(@PathVariable Long id) {
		logger.info("Fetching task with id={}", id);
		return taskService.getTaskById(id);
	}

	// Filter by Status
	@GetMapping("/status/{status}")
	public List<TaskResponseDTO> getTasksByStatus(@PathVariable String status) {
		logger.info("Fetching tasks with status={}", status);
		return taskService.getTasksByStatus(status);
	}

	// Filter by Priority
	@GetMapping("/priority/{priority}")
	public List<TaskResponseDTO> getTasksByPriority(@PathVariable String priority) {
		logger.info("Fetching tasks with priority={}", priority);
		return taskService.getTasksByPriority(priority);
	}

	// Due before a date
	@GetMapping("/due-before")
	public List<TaskResponseDTO> getTasksDueBefore(
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		logger.info("Fetching tasks due before {}", date);
		return taskService.getTasksDueBefore(date);
	}

	// Due between two dates
	@GetMapping("/due-between")
	public List<TaskResponseDTO> getTasksDueBetween(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
		logger.info("Fetching tasks due between {} and {}", start, end);
		return taskService.getTasksDueBetween(start, end);
	}

	// Search by title or description
	@GetMapping("/search")
	public List<TaskResponseDTO> searchTasks(@RequestParam("keyword") String keyword) {
		logger.info("Searching tasks with keyword='{}'", keyword);
		return taskService.searchTasks(keyword);
	}

	@PutMapping("/{id}")
	public TaskResponseDTO updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO requestDTO) {
		logger.info("Updating task id={} with title={}", id, requestDTO.getTitle());
		return taskService.updateTask(id, requestDTO);
	}

	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable Long id) {
		logger.info("Deleting task with id={}", id);
		taskService.deleteTask(id);
	}
}
