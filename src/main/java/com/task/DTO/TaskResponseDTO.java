package com.task.DTO;

import java.util.List;

public class TaskResponseDTO {
	private boolean success;
	private String message;
	private List<String> errors;

	public TaskResponseDTO(boolean success, String message, List<String> errors) {
		this.success = success;
		this.message = message;
		this.errors = errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
