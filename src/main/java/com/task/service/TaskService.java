package com.task.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.Repository.AdminRepository;
import com.task.Repository.StudentRepository;
import com.task.Repository.TaskRepository;
import com.task.enums.Status;
import com.task.model.Admin;
import com.task.model.Student;
import com.task.model.Task;

@Service
public class TaskService {
	
	
	@Autowired
	TaskRepository taskRepository;
	
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
    public Task createTask(Task task) {
		
		return taskRepository.save(task);
	}
	
	public List<Task> getTask()
	{
		return taskRepository.findAll();
	}
	
	

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskDetails(Integer taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public void updateTaskStatus(Integer taskId, Status newStatus) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            task.setStatus(newStatus);
            taskRepository.save(task);
        }
    }

    
	
	
	public Optional<Task> getTaskbyId(Integer id)
	{
		return taskRepository.findById(id);
	}
	
	public Task addTask(Task task) {
		
		return taskRepository.save(task);
	}
	
	public Task updateTask(Integer id,Task task)
	{
		return taskRepository.save(task);
	}
	
	public boolean deleteTask(Integer id) {
		boolean exits = taskRepository.existsById(id);
		if (exits) {
			return true;

		}
		return false;
	}

	

}
