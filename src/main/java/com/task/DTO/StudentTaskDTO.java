package com.task.DTO;

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

    
    // file save

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

    
