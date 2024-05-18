package com.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.task.DTO.ChangePasswordDTO;
import com.task.Repository.StudentRepository;
import com.task.Repository.TokenLogRepository;

import com.task.service.StudentService;
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

	@Autowired
	StudentService studentService;

	@GetMapping("/change-password/{token}")
	public String changePassword(@PathVariable String token, Model model) {
		boolean isValidToken = tokenLogService.isValidToken(token);
		model.addAttribute("isError", !isValidToken);
		model.addAttribute("status", false);
		model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
		return "resetpassword";
	}

	@PostMapping("/change-password/{token}")
	public String changePasswordSave(@PathVariable String token,
			@ModelAttribute("changePasswordDTO") ChangePasswordDTO change, Model model) {
		boolean isValidToken = tokenLogService.isValidToken(token);

		model.addAttribute("isError", false);
		if (!isValidToken) {
			model.addAttribute("isError", !isValidToken);
			return "resetpassword";
		}

		boolean isChangesValid = change != null && change.getNewPassword() != null
				&& change.getConfirmPassword() != null && change.getNewPassword().equals(change.getConfirmPassword());

		if (!isChangesValid) {
			model.addAttribute("status", false);
			model.addAttribute("message", "Password is not valid or matching with confirm password");
			return "resetpassword";
		}

		int id = studentService.getUserIdFromToken(token);
		boolean isPasswordUpdated = studentService.updatePassword(id, change.getNewPassword());

		if (!isPasswordUpdated) {
			model.addAttribute("status", false);
			model.addAttribute("message", "Password is not updating");
			return "resetpassword";
		}

		// invalidate the token
		tokenLogService.invalidateToken(token);

		model.addAttribute("status", true);
		model.addAttribute("message", "Successfully changed password");
		return "resetpassword";
	}

}
