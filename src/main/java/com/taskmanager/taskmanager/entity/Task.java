package com.taskmanager.taskmanager.entity;

import com.taskmanager.taskmanager.enums.Priority;
import com.taskmanager.taskmanager.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "tasks")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taskId;

	@NotBlank(message = "Title can not be empty")
	@Column(unique = true, nullable = false)
	@Size(min = 2, message = "Title must be at least 2 characters")
	private String title;

	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.PENDING;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Priority priority = Priority.MEDIUM;

	@Column(nullable = false)
	private LocalDate dueDate;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
