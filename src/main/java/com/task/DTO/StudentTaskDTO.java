package com.task.DTO;


import org.springframework.web.multipart.MultipartFile;

import com.task.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class StudentTaskDTO {

	private Integer taskId;
	private String taskName;
	private String taskDescription;
	private Integer studentTaskId;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Column(name = "incompletion_reason")
	private String inCompletionReason;

	private MultipartFile file;

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getInCompletionReason() {
		return inCompletionReason;
	}

	public void setInCompletionReason(String inCompletionReason) {
		this.inCompletionReason = inCompletionReason;
	}

	public StudentTaskDTO() {

	}

	public StudentTaskDTO(Integer taskId, String taskName, String taskDescription, Integer studentTaskId) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.studentTaskId = studentTaskId;
	}

	// Getters and setters
	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public Integer getStudentTaskId() {
		return studentTaskId;
	}

	public void setStudentTaskId(Integer studentTaskId) {
		this.studentTaskId = studentTaskId;
	}
}
