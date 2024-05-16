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
        model.addAttribute("isError", !isValidToken);
        model.addAttribute("status", false);
        model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
        return "resetpassword";
    }


    @PostMapping("/change-password/{token}")
    public String changePasswordSave(@PathVariable String token, @ModelAttribute("changePasswordDTO") ChangePasswordDTO change,
                                     Model model) {
        boolean isValidToken = tokenLogService.isValidToken(token);

    	model.addAttribute("isError", false);
        if(!isValidToken) {
        	model.addAttribute("isError", !isValidToken);

            return "resetpassword";
        }
        
        
        boolean isChangesValid = 
        		change != null && 
        		change.getNewPassword() != null && 
        		change.getConfirmPassword() != null && 
        		change.getNewPassword().equals(change.getConfirmPassword());

        if (!isChangesValid) {
        	model.addAttribute("status", false);
        	model.addAttribute("message", "password is not valid or matching with confirm password");
            return "resetpassword";
        }

        String username = getUsernameFromToken(token);
        boolean isPasswordUpdated = updatePassword(username, change.getNewPassword());
        
        if(!isPasswordUpdated) {
        	model.addAttribute("status", false);
        	model.addAttribute("message", "password is not updating");
            return "resetpassword";
        }
        
        // invalidate the token

    	model.addAttribute("status", true);
    	model.addAttribute("message", "successfully changed");
        return "resetpassword";
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
