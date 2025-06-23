package com.taskmanager.taskmanager.service.impl;

import com.taskmanager.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.taskmanager.dto.TaskResponseDTO;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.taskmanager.mapper.TaskMapper;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.service.TaskService;
import com.taskmanager.taskmanager.util.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskMapper taskMapper;

	@Override
	public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
		Task task = taskRepository.save(taskMapper.toEntity(requestDTO));
		return taskMapper.toDTO(task);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDTO> getTasksByUserId(Long userId) {
		return taskRepository.findByUserId(userId)
				.stream()
				.map(taskMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public TaskResponseDTO getTaskById(Long taskId) {
		return taskMapper.toDTO(taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId)));
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDTO> getTasksByStatus(String status) {
		return taskRepository.findByStatus(EnumUtils.parseStatus(status))
				.stream().map(taskMapper::toDTO)
				.collect(Collectors.toList()); //Converts each Task to TaskResponseDTO
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDTO> getTasksByPriority(String priority) {
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
		return taskRepository.findByDueDateBetween(start, end)
				.stream().map(taskMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDTO> searchTasks(String keyword) {
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
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

		updateTaskFromDTO(task, updatedDTO);
		return taskMapper.toDTO(taskRepository.save(task));
	}

	@Override
	public void deleteTask(Long taskId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
		taskRepository.delete(task);
	}
}
