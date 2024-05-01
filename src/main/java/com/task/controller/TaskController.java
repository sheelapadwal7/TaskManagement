package com.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.model.Task;
import com.task.service.AuthService;
import com.task.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@Autowired
    private AuthService authService;

	@GetMapping
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> tasks = taskService.getAllTasks();
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<Task> getTaskDetails(@PathVariable Integer taskId) {
		Task task = taskService.getTaskDetails(taskId);
		if (task != null) {
			return ResponseEntity.ok(task);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@PostMapping("/addtask")
    public ResponseEntity<Task> createTask(@RequestBody Task task,
                                            @RequestHeader("username") String username,
                                            @RequestHeader("password") String password) {
        if (authService.isAdmin(username, password)) {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.ok(createdTask);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {

		boolean deleted = taskService.deleteTask(id);

		if (deleted) {
			return ResponseEntity.ok("Task with ID " + id + " deleted successfully.");
		}

		else

		{

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + id + " not found.");

		}

	}
}
