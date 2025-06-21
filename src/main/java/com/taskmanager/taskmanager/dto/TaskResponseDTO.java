package com.taskmanager.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskResponseDTO {
	private Long id;
	private String title;
	private String description;
	private String status;
	private String priority;
	private LocalDate dueDate;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
