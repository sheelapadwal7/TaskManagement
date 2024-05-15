package com.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("auth/web")
@Controller
public class AuthWebController {

	@GetMapping("change-password/{token}")
	public String changePassword(@PathVariable String token, Model model) {
		
		//token validation
		//
		model.addAttribute("status", true);
		model.addAttribute("token", token);
		
		return "change-password";
	}
	
	@PostMapping("change-password/{token}")
	public String changePasswordSave(@ModelAttribute("employee") ChangePasswordDTO change) {
		//token validation
		//
		
		//validate changes
		
		// get username and update
		
		// update message
		
		model.addAttribute("status", true);
		model.addAttribute("token", token);
		
		return "change-password";
		
	}
	
}
