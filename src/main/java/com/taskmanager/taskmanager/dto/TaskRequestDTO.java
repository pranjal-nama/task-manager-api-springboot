package com.taskmanager.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequestDTO {
	private String title;
	private String description;
	private String status;
	private String priority;
	private LocalDate dueDate;
}
