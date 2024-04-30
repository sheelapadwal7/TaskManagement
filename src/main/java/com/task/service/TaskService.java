package com.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.Repository.AdminRepository;
import com.task.Repository.StudentRepository;
import com.task.Repository.TaskRepository;
import com.task.enums.Status;
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

	
	
	public boolean startTask(Integer taskId) {
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		if (optionalTask.isPresent()) {
			Task task = optionalTask.get();
			task.setStatus(Status.INPROGRESS);
			taskRepository.save(task);
			return true;
		} else {
			return false;
		}
	}

	public boolean statusUpdateTask(Integer taskId,Task task) {
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		if (optionalTask.isPresent()) {
			Task task1 = optionalTask.get();
			task1.setStatus(Status.COMPLETED);
			task1.setInCompletionReason(task.getInCompletionReason());
			taskRepository.save(task1);
			return true;
		} else {
			return false;
		}
	}

	public List<Task> getTask() {
		return taskRepository.findAll();
	}

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public Task getTaskDetails(Integer taskId) {
		return taskRepository.findById(taskId).orElse(null);
	}

	public Optional<Task> getTaskbyId(Integer id) {
		return taskRepository.findById(id);
	}

	public Task addTask(Task task) {

		return taskRepository.save(task);
	}

	public Task updateTask(Integer id, Task task) {
		return taskRepository.save(task);
	}

	public boolean deleteTask(Integer id) {
		boolean exits = taskRepository.existsById(id);
		if (exits) {
			return true;

		}
		return false;
	}
	
	public boolean startTask(Integer taskId) {
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		if (optionalTask.isPresent()) {
			Task task = optionalTask.get();
			task.setStatus(Status.INPROGRESS);
			taskRepository.save(task);
			return true;
		} else {
			return false;
		}
	}

	public boolean completeTask(Integer taskId) {
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		if (optionalTask.isPresent()) {
			Task task = optionalTask.get();
			task.setStatus(Status.COMPLETED);
			taskRepository.save(task);
			return true;
		} else {
			return false;
		}
	}
	
	
	
	

}
