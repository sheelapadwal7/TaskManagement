package com.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.DTO.LoginRequestDTO;
import com.task.Repository.StudentRepository;
import com.task.Repository.StudentTaskRepository;
import com.task.Repository.TaskRepository;
import com.task.enums.Status;
import com.task.model.Student;

import com.task.model.Task;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentrepository;
	@Autowired
	TaskRepository taskRepository;

	@Autowired
	StudentTaskRepository studentTaskRepository;

	public Student login(LoginRequestDTO loginRequestDto) {
		Optional<Student> studentO = studentrepository.findByUserName(loginRequestDto.getUserName());

		System.out.println(studentO);
		Student student = null;

		if (studentO.isPresent()) {

			Student studentdb = studentO.get();

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
//        	System.out.print("passwrod user: " + loginRequestDto.getPassword() + " from db:" + studentdb.getPassword());
			if (passwordEncoder.matches(loginRequestDto.getPassword(), studentdb.getPassword())) {
				student = studentdb;
			}

		}

		return student;
	}

	public List<Student> getStudent() {
		return studentrepository.findAll();

	}

	public Optional<Student> getStudentById(Integer id) {
		return studentrepository.findById(id);
	}

	public Student addStudent(Student student) {

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = bCryptPasswordEncoder.encode(student.getPassword());
		student.setPassword(hashedPassword);

		// Save the student to the database Student savedStudent =
		return studentrepository.save(student);

	}

	
	public Student update(Integer id, Student student) {
		Student existingstudent = studentrepository.findById(id).orElse(null);
		existingstudent.setFirstName(student.getFirstName());
		existingstudent.setFirstName(student.getFirstName());
		existingstudent.setEmail(student.getEmail());
		existingstudent.setContactNumber(student.getContactNumber());
		existingstudent.setDob(student.getDob());

		existingstudent.setPassword(student.getPassword());
		existingstudent.setUserName(student.getUserName());
		return studentrepository.save(existingstudent);
	}

	public boolean deletestudent(Integer id) {
		boolean exits = studentrepository.existsById(id);
		if (exits) {
			return true;

		}
		return false;
	}
	
	
}

