package com.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.util.CommunicationUtil;

@Component
public class CommunicationService {

	@Autowired
	CommunicationUtil communicationUtil;
	
	
	public void sendResetEmail(String userName, String toEmail, String token) {
		
		String tokenLink = "http://localhost:8080/change-password.html?token=" + token;
		
		String subject = "Reset Email";
		String body = "Hi ${user},<br/><br/>"
				+ "Please use the following link to reset your password:  <a href=${tokenLink}>click here</a>.<br/><br/>"
				+ "Best Regards, <br/>Team TMS";
		
		body = body.replace("${user}", userName);
		body = body.replace("${tokenLink}", tokenLink);
		
		communicationUtil.sendEmail(toEmail, subject, body);
	}
	
	
}