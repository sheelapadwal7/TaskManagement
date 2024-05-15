package com.task.service;

import java.time.LocalDateTime;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.DTO.LoginRequestDTO;
import com.task.DTO.LoginResponseDTO;
import com.task.Repository.StudentRepository;
import com.task.Repository.StudentTaskRepository;
import com.task.Repository.TaskRepository;
import com.task.Repository.TokenLogRepository;
import com.task.enums.LinkType;
import com.task.model.Student;
import com.task.model.TokenLog;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentrepository;
	@Autowired
	TaskRepository taskRepository;

	@Autowired
	StudentTaskRepository studentTaskRepository;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	TokenLogService tokenlogservice;
	
	@Autowired
	TokenLogRepository tokenLogRepository;
	
	@Autowired
	CommunicationService communicationService;

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

	public boolean isStudentValid(Student student) {
		return student != null && !"LOCKED".equals(student.getAccountStatus());
	}

	public void handleIncorrectPassword(LoginResponseDTO loginResponseDto, Student student) {
		int loginAttempts = student.getLoginAttempts() + 1;
		student.setLoginAttempts(loginAttempts);

		if (loginAttempts >= 3) {
			lockAccount(student);
			loginResponseDto.setMessage("Incorrect password. Account is locked due to multiple login attempts.");
		} else {
			saveStudent(student);
			loginResponseDto.setMessage("Incorrect password. Please try again.");
		}
		loginResponseDto.setStatus(false);
	}

	public void lockAccount(Student student) {
		student.setAccountStatus("LOCKED");
		student.setLockDateTime(LocalDateTime.now());
		saveStudent(student);
	}

	public void resetLoginAttempts(Student student) {
		student.setLoginAttempts(0);
		saveStudent(student);
	}

	public LoginResponseDTO createInvalidStudentResponse(LoginResponseDTO loginResponseDto, Student student) {
		loginResponseDto.setStatus(false);

		String message = "Account is locked. Please try again after 24 hours.";
		if ("LOCKED".equals(student.getAccountStatus()) && isAccountLocked(student)) {
			student.setAccountStatus("ACTIVE");
			student.setLoginAttempts(0);
			saveStudent(student);
			message = "Account unlocked. Please try again.";
		}

		loginResponseDto.setMessage(message);
		return loginResponseDto;
	}

	public boolean isAccountLocked(Student student) {
		LocalDateTime lockDateTime = student.getLockDateTime();
		LocalDateTime currentDateTime = LocalDateTime.now();
		return lockDateTime.plusHours(24).isBefore(currentDateTime);
	}

	public Student findStudentByUsername(String username) {
		return studentrepository.findByUserName(username).orElse(null);
	}

	public boolean verifyPassword(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	public void generateTokenAndSendEmail(String email) {
		Optional<Student> studentOptional = studentrepository.findByEmail(email);
		if (studentOptional.isPresent()) {
			Student student = studentOptional.get();
			String token = tokenlogservice.generateToken(student);
			studentrepository.save(student);


			communicationService.sendResetEmail(student.getFirstName() , student.getEmail(), token);
			
		} else {
			System.out.println("Email not found");
		}
	}

	public void sendEmail(String userEmail, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(userEmail);
		message.setSubject("Password Reset Request");
		message.setText(
				"Please use the following link to reset your password:  http://localhost:8080/change-password.html?token="
						+ token);
		javaMailSender.send(message);
	}

	

	public void resetPassword(String token, String newPassword, String confirmPassword) {
        if (newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            throw new IllegalArgumentException("Password fields cannot be empty");
        }

        Optional<TokenLog> tokenLogOptional = tokenLogRepository.findByTokenAndLinkType(token, LinkType.STUDENT);
        if (!tokenLogOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid token");
        }

        TokenLog tokenLog = tokenLogOptional.get();

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (tokenLog.getLinkType() != LinkType.STUDENT) {
            throw new IllegalArgumentException("Invalid link type for password reset");
        }

        
        int studentId = tokenLog.getLinkId();

        
        Optional<Student> studentOptional = studentrepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            throw new IllegalArgumentException("No student found with the provided token and link type");
        }

        // Update the password for the Student entity
        Student student = studentOptional.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        student.setPassword(encodedPassword);
        studentrepository.save(student);

        // Delete the TokenLog after password reset
        tokenLogRepository.delete(tokenLog);
    }
	public void saveStudent(Student student) {
		studentrepository.save(student);
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
