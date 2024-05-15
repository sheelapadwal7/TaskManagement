package com.task.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;


@Component
public class CommunicationUtil {
	
	
	@Autowired
	JavaMailSender mailSender;


	public boolean sendEmail(String toEmail, String subject, String body) {

		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			
			
			helper.setText(body, true); // Use this or above line.
			helper.setTo(toEmail);
			helper.setSubject("TMS - " + subject);
			mailSender.send(mimeMessage);
			

			System.out.println("Sent email for " + subject + " to " + toEmail);
			return true;
			
		} catch(Exception ex) {
			
			System.out.println("Failed to Send email for " + subject + " to " + toEmail);
			System.out.println("Exception: " + ex.getStackTrace());
			
			return false;
		}
	}
	
}
