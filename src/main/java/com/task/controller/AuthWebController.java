package com.task.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.task.DTO.ChangePasswordDTO;
import com.task.Repository.StudentRepository;
import com.task.Repository.TokenLogRepository;
import com.task.model.Student;
import com.task.model.TokenLog;
import com.task.service.TokenLogService;

@RequestMapping("/auth/web")
@Controller
public class AuthWebController {

    @Autowired
    private TokenLogService tokenLogService;
    @Autowired
	TokenLogRepository tokenLogRepository;
    
    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/change-password/{token}")
    public String changePassword(@PathVariable String token, Model model) {
        boolean isValidToken = tokenLogService.isValidToken(token);
        model.addAttribute("isValidToken", isValidToken);
        model.addAttribute("token", token);
        model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
        return "resetpassword";
    }


    @PostMapping("/change-password/{token}")
    public String changePasswordSave(@PathVariable String token, @ModelAttribute("changePasswordDTO") ChangePasswordDTO change,
                                     Model model) {
        boolean isValidToken = tokenLogService.isValidToken(token);
        boolean isChangesValid = validateChanges(change);

        if (isValidToken && isChangesValid) {
            String username = getUsernameFromToken(token);
            boolean isPasswordUpdated = updatePassword(username, change.getPassword());

            if (isPasswordUpdated) {
                return "redirect:/resetpassword.html?status=success";
            }
        }

        model.addAttribute("error", !isValidToken ? "Invalid token. Please try again." : "Invalid changes. Please fill all required fields.");
        model.addAttribute("isValidToken", isValidToken);
        model.addAttribute("token", token);
        return "resetpassword";
    }

    private boolean validateChanges(ChangePasswordDTO change) {
        return change != null && change.getPassword() != null;
    }

    private String getUsernameFromToken(String token) {
        Optional<TokenLog> tokenLogOptional = tokenLogRepository.findByToken(token);

        if (tokenLogOptional.isPresent()) {
            TokenLog tokenLog = tokenLogOptional.get();
            return tokenLog.getUserName(); // Assuming the username is stored in the TokenLog entity
        }
        return null;
    }

    public boolean updatePassword(String username, String newPassword) {
        Optional<Student> userOptional = studentRepository.findByUserName(username);
        
        if (userOptional.isPresent()) {
        	Student student = userOptional.get();
        	student.setPassword(newPassword); // Update the password
        	studentRepository.save(student); // Save the updated user entity
            return true; // Password updated successfully
        } else {
            return false; // User not found
        }
    }
}
