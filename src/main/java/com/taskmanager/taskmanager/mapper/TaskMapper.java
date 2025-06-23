package com.taskmanager.taskmanager.mapper;

import com.taskmanager.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.taskmanager.dto.TaskResponseDTO;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.Priority;
import com.taskmanager.taskmanager.enums.Status;
import com.taskmanager.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

	@Autowired
	private UserRepository userRepository;

	// DTO ➡ Entity
	public Task toEntity(TaskRequestDTO dto) {
		User user = userRepository.findById(dto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));

		return Task.builder()
				.title(dto.getTitle())
				.description(dto.getDescription())
				.status(Status.valueOf(dto.getStatus()))
				.priority(Priority.valueOf(dto.getPriority()))
				.dueDate(dto.getDueDate())
				.user(user)
				.build();
	}

	// Entity ➡ DTO
	public TaskResponseDTO toDTO(Task task) {
		TaskResponseDTO dto = new TaskResponseDTO();

		dto.setId(task.getTaskId());
		dto.setTitle(task.getTitle());
		dto.setDescription(task.getDescription());
		dto.setStatus(task.getStatus().name());
		dto.setPriority(task.getPriority().name());
		dto.setDueDate(task.getDueDate());
		dto.setCreatedAt(task.getCreatedAt());
		dto.setUpdatedAt(task.getUpdatedAt());
		return dto;
	}
}
