package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.enums.Priority;
import com.taskmanager.taskmanager.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUserId(Long userId);

	List<Task> findByStatus(Status status);

	List<Task> findByPriority(Priority priority);

	List<Task> findByDueDateBefore(LocalDate date);

	List<Task> findByDueDateBetween(LocalDate start, LocalDate end);

	@Query(value = "SELECT t FROM Task t WHERE LOWER(t.title) LIKE %:keyword% OR LOWER(t.description) LIKE %:keyword%")
	List<Task> searchByKeyword(@Param("keyword") String keyword);
}
