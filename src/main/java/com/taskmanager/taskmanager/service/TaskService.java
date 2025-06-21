package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.taskmanager.dto.TaskResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface TaskService {

	TaskResponseDTO createTask(TaskRequestDTO requestDTO);
	TaskResponseDTO getTaskById(Long taskId);
	TaskResponseDTO getTaskByTitle(String title);
	List<TaskResponseDTO> getAllTasks();
	TaskResponseDTO updateTask(Long taskId, TaskRequestDTO updatedDTO);
	void deleteTask(Long taskId);
	void deleteTaskByTitle(String title);

	List<TaskResponseDTO> getTasksByStatus(String status);
	List<TaskResponseDTO> getTasksByPriority(String priority);
	List<TaskResponseDTO> getTasksDueBefore(LocalDate date);
	List<TaskResponseDTO> getTasksDueBetween(LocalDate start, LocalDate end);
	List<TaskResponseDTO> searchTasks(String keyword);
}
