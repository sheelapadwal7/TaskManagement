<<<<<<< Updated upstream
package com.task.controller;

=======

package com.task.controller;

import java.util.HashMap;
import java.util.Map;

>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

<<<<<<< Updated upstream

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
	
=======
import com.task.DTO.ChangePasswordDTO;

@RequestMapping("auth/web")

@Controller
public class AuthWebController {

	private Map<String, String> tokenUsernameMap;

	public AuthWebController() {
		tokenUsernameMap = new HashMap<>();
		// Populate tokenUsernameMap with some sample data for demonstration
		tokenUsernameMap.put("token123", "user123");
		tokenUsernameMap.put("token456", "user456");
	}

	@GetMapping("change-password/{token}")
	public String changePassword(@PathVariable String token, Model model) {
		boolean isValidToken = validateToken(token);

		model.addAttribute("isValidToken", isValidToken);
		model.addAttribute("token", token);

		return "change-password";
	}

	@PostMapping("change-password/{token}")
	public String changePasswordSave(@PathVariable String token, @ModelAttribute("employee") ChangePasswordDTO change,
			Model model) {
		boolean isValidToken = validateToken(token);

		if (isValidToken) {
			boolean isChangesValid = validateChanges(change);

			if (isChangesValid) {
				String username = getUsernameFromToken(token);
				updatePassword(username, change.getPassword());

				model.addAttribute("status", true);
			}
		}

		model.addAttribute("isValidToken", isValidToken);
		model.addAttribute("token", token);

		return "change-password";
	}

	private boolean validateToken(String token) {

		return token != null && tokenUsernameMap.containsKey(token);
	}

	private boolean validateChanges(ChangePasswordDTO change) {

		return change != null && change.getPassword() != null;
	}

	private String getUsernameFromToken(String token) {

		return tokenUsernameMap.get(token);
	}

	private void updatePassword(String username, String newPassword) {

		System.out.println("Updating password for user: " + username + " to: " + newPassword);
	}

>>>>>>> Stashed changes
}
