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

@Table(name = "tasks", indexes = {
		@Index(name = "idx_task_title", columnList = "title"),
		@Index(name = "idx_task_due_Date", columnList = "dueDate"),
		@Index(name = "idx_task_status", columnList = "status")
})
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taskId;

	@NotBlank(message = "Title can not be empty")
	@Column(nullable = false)
	@Size(min = 2, message = "Title must be at least 2 characters")
	private String title;

	private String description;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.PENDING;

	@Builder.Default
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
