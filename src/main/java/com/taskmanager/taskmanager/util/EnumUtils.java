package com.taskmanager.taskmanager.util;

import com.taskmanager.taskmanager.enums.Priority;
import com.taskmanager.taskmanager.enums.Status;
import com.taskmanager.taskmanager.exception.ResourceNotFoundException;

public class EnumUtils {

	public static Status parseStatus(String status) {
		try {
			return Status.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Status", "value", status);
		}
	}

	public static Priority parsePriority(String priority) {
		try {
			return Priority.valueOf(priority.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Priority", "value", priority);
		}
	}
}
