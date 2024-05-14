package com.task.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.DTO.LoginRequestDTO;
import com.task.DTO.LoginResponseDTO;
import com.task.DTO.UserDTO;
import com.task.model.Admin;
import com.task.model.Student;
//import com.task.security.JwtService;
import com.task.service.AdminService;
import com.task.service.StudentService;
import com.task.service.TokenLogService;

@RestController

@RequestMapping("/auth")
public class AuthController {

	@Autowired
	StudentService studentService;

	@Autowired
	TokenLogService tokenlogservice;
	

	//@Autowired
	//JwtService jwtService;

	@Autowired
	AdminService adminService;

	@GetMapping("converttohash")
	public String convertToHash(@RequestParam String clearText) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String cipherText = passwordEncoder.encode(clearText);

		return cipherText;

	}

	
	
	
	/*
	 * @PostMapping("/student/login") public LoginResponseDTO
	 * studentLogin(@RequestBody LoginRequestDTO loginRequestDto) {
	 * 
	 * LoginResponseDTO loginResponseDto = new LoginResponseDTO();
	 * 
	 * // Logic flow start Student student = studentService.login(loginRequestDto);
	 * 
	 * // if not found send error if (student == null) {
	 * loginResponseDto.setStatus(false);
	 * loginResponseDto.setMessage("User credentials are not correct"); return
	 * loginResponseDto; }
	 * 
	 * // generate token //String token = jwtService.generateToken(student); String
	 * token = tokenlogservice.generateToken();
	 * 
	 * // Response preparation UserDTO userDto = new UserDTO();
	 * userDto.setFirstName(student.getFirstName());
	 * userDto.setUserName(student.getUserName());
	 * 
	 * loginResponseDto.setStatus(true);
	 * loginResponseDto.setMessage(" Student Login Successfully");
	 * loginResponseDto.setUser(userDto); loginResponseDto.setToken(token);
	 * //Response preparation end
	 * 
	 * // Response send return loginResponseDto;
	 * 
	 * }
	 */
	  
	 

	@PostMapping("/student/login")
	public LoginResponseDTO studentLogin(@RequestBody LoginRequestDTO loginRequestDto) {

	    LoginResponseDTO loginResponseDto = new LoginResponseDTO();

	    // Logic flow start
	    Student student = studentService.login(loginRequestDto);

	    // if not found send error
	    if (student == null) {
	        loginResponseDto.setStatus(false);
	        loginResponseDto.setMessage("User credentials are not correct");
	        return loginResponseDto;
	    }

	    try {
	        // Check for null or empty input
	        if (loginRequestDto.getUserName() == null || loginRequestDto.getUserName().isEmpty() ||
	                loginRequestDto.getPassword() == null || loginRequestDto.getPassword().isEmpty()) {
	            throw new IllegalArgumentException("Username or password cannot be empty.");
	        }

	        // Check if account is locked
	        if (student.getAccountStatus().equals("locked")) {
	            LocalDateTime currentTime = LocalDateTime.now();
	            LocalDateTime unlockTime = student.getLockedDateTime().plusHours(24);
	            if (currentTime.isBefore(unlockTime)) {
	                throw new RuntimeException("Account locked. Please try again later.");
	            } else {
	                student.setAccountStatus("active");
	                student.setLoginAttempts(0);
	                student.setLockedDateTime(null);
	            }
	        }

	        // Check password
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        if (passwordEncoder.matches(loginRequestDto.getPassword(), student.getPassword())) {
	            // Reset login attempts and update last login
	            student.setLoginAttempts(0);
	            student.setLockedDateTime(LocalDateTime.now());

	            // Generate token
	            String token = tokenlogservice.generateToken();

	            // Response preparation
	            UserDTO userDto = new UserDTO();
	            userDto.setFirstName(student.getFirstName());
	            userDto.setUserName(student.getUserName());

	            loginResponseDto.setStatus(true);
	            loginResponseDto.setMessage("Student Login Successfully");
	            loginResponseDto.setUser(userDto);
	            loginResponseDto.setToken(token);

	        } else {
	            // Increment login attempts and lock account if attempts exceed 3
	            studentService.incrementLoginAttempts(student);
	            if (student.getLoginAttempts() >= 3) {
	                student.setAccountStatus("locked");
	                student.setLockedDateTime(LocalDateTime.now());
	            }

	            throw new RuntimeException("Incorrect password.");
	        }
	    } catch (Exception e) {
	        loginResponseDto.setStatus(false);
	        loginResponseDto.setMessage(e.getMessage());
	    }

	    // Response send
	    return loginResponseDto;
	}

	@PostMapping("/admin/login")
	public LoginResponseDTO adminlogin(@RequestBody LoginRequestDTO loginRequestDto) {
		System.out.println("1");
		LoginResponseDTO loginResponseDto = new LoginResponseDTO();
		System.out.println("2");
		// Logic flow start
		Admin admin = adminService.login(loginRequestDto);
		System.out.println("5");
		// if not found send error
		if (admin == null) {
			loginResponseDto.setStatus(false);
			loginResponseDto.setMessage("User credentials are not correct");
			return loginResponseDto;
		}

		// generate token
		String token = tokenlogservice.generateToken();

		// Response preparation
		UserDTO userDto = new UserDTO();
		userDto.setFirstName(admin.getFirstName());

		userDto.setUserName(admin.getUserName());

		loginResponseDto.setStatus(true);
		loginResponseDto.setMessage(" Admin Login Successfully");
		loginResponseDto.setUser(userDto);
		loginResponseDto.setToken(token);
		// Response preparation end

		// Response send
		return loginResponseDto;

	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestParam String token) {
		if (tokenlogservice.invalidateToken(token)) {
			return ResponseEntity.ok("Logout successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found");
		}
	}

}
