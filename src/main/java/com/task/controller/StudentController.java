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

import com.task.model.Student;
import com.task.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentService studentservice;

	@GetMapping("")
	public ResponseEntity<?> getStudent(@RequestBody Student student) {

		List<Student> students = studentservice.getStudent();
		if (students.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
		}
		return ResponseEntity.ok(students);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Integer id) {

		Optional<Student> studentById = studentservice.getStudentById(id);
		if (studentById.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student by id not found");

		} else {
			Student student = studentById.get();

			return ResponseEntity.ok().body(student);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable Integer id, @RequestBody Student student) {

		// Check if student exists
		Optional<Student> existingStudent = studentservice.getStudentById(id);
		if (!existingStudent.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with id " + id + "not found.");
		}

		// Update the student
		studentservice.update(id, student);
		return ResponseEntity.ok().body("Student with ID " + id + " updated successfully.");
	}

	
	@PostMapping(" ")
	public ResponseEntity<?> addStudent(@RequestBody Student student) {

		System.out.println("first");
		studentservice.addStudent(student);
		System.out.println("last");
		return ResponseEntity.ok().body("Student added successfully.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {

		boolean deleted = studentservice.deletestudent(id);

		if (deleted) {
			return ResponseEntity.ok("Student with ID " + id + " deleted successfully.");
		}

		else

		{

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with id " + id + " not found.");

		}

	}
}
