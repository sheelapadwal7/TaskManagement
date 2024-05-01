/*
  * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

import com.task.model.Admin;
import com.task.model.Task;
import com.task.service.AdminService;
import com.task.service.TaskService;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	TaskService taskService;

	@PostMapping("/addtask")
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		Task createdTask = taskService.createTask(task);
		return ResponseEntity.ok(createdTask);
	}

	@DeleteMapping("/delete/task/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Integer taskId) {
		adminService.deleteTask(taskId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Integer StudentId) {
		adminService.deleteStudent(StudentId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProfessor(@PathVariable Integer ProfessorId) {
		adminService.deleteProfessor(ProfessorId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("")
	public ResponseEntity<?> getAdmin() {
		List<Admin> studentCount = adminService.getAdmin();
		if (studentCount.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(adminService.getAdmin(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAdminById(@PathVariable Integer id) {
		Optional<Admin> res = adminService.getAdminById(id);
		if (res.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {

			Admin admin = res.get();

			return ResponseEntity.ok().body(admin);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {

		adminService.AddAdmin(admin);
		return ResponseEntity.ok().body("Admin added successfully.");

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody Admin admin) {
		adminService.updateAdmin(id, admin);
		return ResponseEntity.ok().body("Updated Succesfully");

	}

}
