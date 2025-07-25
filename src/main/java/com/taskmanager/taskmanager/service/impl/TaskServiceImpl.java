package com.taskmanager.taskmanager.service.impl;

import com.taskmanager.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.taskmanager.dto.TaskResponseDTO;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.taskmanager.mapper.TaskMapper;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.service.TaskService;
import com.taskmanager.taskmanager.util.EnumUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
@Service
public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskMapper taskMapper;

	@Override
	public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
		logger.info("Creating task for userId={}, title={}",
				requestDTO.getUserId(), requestDTO.getTitle());
		Task task = taskRepository.save(taskMapper.toEntity(requestDTO));
		logger.debug("Task created");
		return taskMapper.toDTO(task);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDTO> getTasksByUserId(Long userId) {
		logger.info("Fetching tasks for userId={}", userId);
		return taskRepository.findByUserId(userId)
				.stream()
				.map(taskMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public TaskResponseDTO getTaskById(Long taskId) {
		logger.info("Fetching task with ID={}", taskId);
		return taskMapper.toDTO(taskRepository.findById(taskId)
				.orElseThrow(() -> {
					logger.warn("Task not found with ID={}", taskId);
					return new ResourceNotFoundException("Task", "id", taskId);
				}));
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDTO> getTasksByStatus(String status) {
		logger.info("Fetching tasks with status={}", status);
		return taskRepository.findByStatus(EnumUtils.parseStatus(status))
				.stream().map(taskMapper::toDTO)
				.collect(Collectors.toList()); //Converts each Task to TaskResponseDTO
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDTO> getTasksByPriority(String priority) {
		logger.info("Fetching tasks with priority={}", priority);
		return taskRepository.findByPriority(EnumUtils.parsePriority(priority))
				.stream().map(taskMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDTO> getTasksDueBefore(LocalDate date) {
		return taskRepository.findByDueDateBefore(date)
				.stream().map(taskMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDTO> getTasksDueBetween(LocalDate start, LocalDate end) {
		logger.info("Fetching tasks due between {} and {}", start, end);
		return taskRepository.findByDueDateBetween(start, end)
				.stream().map(taskMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDTO> searchTasks(String keyword) {
		logger.info("Searching tasks with keyword={}", keyword);
		return taskRepository.searchByKeyword(keyword.toLowerCase())
				.stream().map(taskMapper::toDTO).collect(Collectors.toList());
	}

	private void updateTaskFromDTO(Task task, TaskRequestDTO dto) {
		task.setTitle(dto.getTitle());
		task.setDescription(dto.getDescription());
		task.setStatus(EnumUtils.parseStatus(dto.getStatus()));
		task.setPriority(EnumUtils.parsePriority(dto.getPriority()));
		task.setDueDate(dto.getDueDate());
	}

	@Override
	public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO updatedDTO) {
		logger.info("Updating task with ID={}", taskId);
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> {
					logger.warn("Cannot update. Task not found with ID={}", taskId);
					return new ResourceNotFoundException("Task", "id", taskId);
				});

		updateTaskFromDTO(task, updatedDTO);
		logger.debug("Task updated: title={}", updatedDTO.getTitle());
		return taskMapper.toDTO(taskRepository.save(task));
	}

	@Override
	public void deleteTask(Long taskId) {
		logger.info("Deleting task with ID={}", taskId);
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> {
					logger.warn("Cannot delete. Task not found with ID={}", taskId);
					return new ResourceNotFoundException("Task", "id", taskId);
				});
		taskRepository.delete(task);
	}
}
