package com.task.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.enums.Status;
import com.task.model.Admin;
import com.task.model.Student;
import com.task.model.Task;
import com.task.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	
	
	@Autowired
	TaskService taskService;
	
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

    @PutMapping("/{taskId}/status")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable Integer taskId, @RequestBody Status newStatus) {
        taskService.updateTaskStatus(taskId, newStatus);
        return ResponseEntity.ok().build();
    }

	
	@GetMapping("/get")
	public ResponseEntity<?> getTask(@RequestBody Task task) {

		List<Task> adminL = taskService.getTask();
		if (adminL.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("admin not found");
		}
		return ResponseEntity.ok(task);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTaskById(@PathVariable Integer id) {

		Optional<Task> taskId = taskService.getTaskbyId(id);
		if (taskId==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("task by id not found");

		} else {
			Task task = taskId.get();

			return ResponseEntity.ok().body(task);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable Integer id, @RequestBody Task task) {

		// Check if student exists
		Task taskex = taskService.updateTask(id, task);
		if (taskex==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("task with id " + id + "not found.");
		}

		// Update the student
		taskService.updateTask(id, task);
		return ResponseEntity.ok().body("task with ID " + id + " updated successfully.");
	}

	
	@PostMapping("/add")
	public ResponseEntity<?> createTask(@RequestBody Task task) {
		taskService.createTask(task);
		return ResponseEntity.ok().body("Task added successfully.");
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


