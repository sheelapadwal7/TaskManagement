package com.task.controller;

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

import jakarta.servlet.http.HttpSession;

@RestController

@RequestMapping("/auth")
public class AuthController {

	@Autowired
	StudentService studentservice;

	@Autowired
	TokenLogService tokenlogservice;

	// @Autowired
	// JwtService jwtService;

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
	 * // Logic flow start Student student = studentservice.login(loginRequestDto);
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
	 * 
	 */
	@PostMapping("/student/login")
	public LoginResponseDTO studentLogin(@RequestBody LoginRequestDTO loginRequestDto) {

		LoginResponseDTO loginResponseDto = new LoginResponseDTO();

		if (loginRequestDto.getUserName() == null || loginRequestDto.getUserName().isEmpty()
				|| loginRequestDto.getPassword() == null || loginRequestDto.getPassword().isEmpty()) {
			loginResponseDto.setStatus(false);
			loginResponseDto.setMessage("Username or password cannot be empty");
			return loginResponseDto;
		}

		Student student = studentservice.findStudentByUsername(loginRequestDto.getUserName());

		if (!studentservice.isStudentValid(student)) {

			return studentservice.createInvalidStudentResponse(loginResponseDto, student);
		}

		// Check if password matches
		boolean passwordMatches = studentservice.verifyPassword(loginRequestDto.getPassword(), student.getPassword());
		if (!passwordMatches) {
			// Increment login attempts
			studentservice.handleIncorrectPassword(loginResponseDto, student);
			return loginResponseDto;
		}

		// Reset login attempts upon successful login
		studentservice.resetLoginAttempts(student);

		// Generate token or session
		String token = tokenlogservice.generateToken(student);

		// Response preparation
		UserDTO userDto = new UserDTO();
		userDto.setFirstName(student.getFirstName());
		userDto.setUserName(student.getUserName());

		loginResponseDto.setStatus(true);
		loginResponseDto.setMessage("Student Login Successfully");
		loginResponseDto.setUser(userDto);
		loginResponseDto.setToken(token);

		// Response send
		return loginResponseDto;
	}
	
	
	@PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        studentservice.generateTokenAndSendEmail(email);
        return ResponseEntity.ok("Password reset link sent to your email");
    }
	
	
	/*
	 * @PostMapping("/change-password") public String changePassword(@RequestParam
	 * String email,
	 * 
	 * @RequestParam String newPassword,
	 * 
	 * @RequestParam String confirmPassword) {
	 * 
	 * if (!newPassword.equals(confirmPassword)) { return
	 * "Error: New password and confirmed password do not match"; }
	 * 
	 * studentservice.changePassword(email,newPassword, confirmPassword); return
	 * "Password changed successfully"; }
	 */
	
	@PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token,
                                                 @RequestParam("newPassword") String newPassword,
                                                 @RequestParam("confirmPassword") String confirmPassword) {
        try {
            studentservice.resetPassword(token, newPassword, confirmPassword);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
		//String token = tokenlogservice.generateToken();

		// Response preparation
		UserDTO userDto = new UserDTO();
		userDto.setFirstName(admin.getFirstName());

		userDto.setUserName(admin.getUserName());

		loginResponseDto.setStatus(true);
		loginResponseDto.setMessage(" Admin Login Successfully");
		loginResponseDto.setUser(userDto);
		//loginResponseDto.setToken(token);
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
