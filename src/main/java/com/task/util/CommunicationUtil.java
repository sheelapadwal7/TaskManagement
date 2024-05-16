
package com.task.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.mail.internet.MimeMessage;

	
	
	
@Component
public class CommunicationUtil {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	RestTemplate restTemplate;
	
	 @Value("${sms.url}") 
	    String url;

	    @Value("${sms.key}")
	    String apiKey;


	public boolean sendEmail(String toEmail, String subject, String body) {

		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

			
			
			helper.setText(body, true); 
			helper.setTo(toEmail);
			helper.setSubject("TMS - " + subject);
			mailSender.send(mimeMessage);
			

			System.out.println("Sent email for " + subject + " to " + toEmail);
			return true;
			
		} 
		 
		catch (Exception ex) {

			System.out.println("Failed to Send email for " + subject + " to " + toEmail);
			System.out.println("Exception: " + ex.getStackTrace());


			return false;
		}
	}
	

	
	public void sendSMS(String mobile, String content) {

		
		try {
			
			String body = "{\"sender\": \"MyShop\", \"recipient\": \"+91"+ mobile +"\", \"content\": \""+ content +"\", \"type\": \"transactional\"}";
			

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("api-key", apiKey);
			
			HttpEntity<String> request = new HttpEntity<>(body, headers);
			
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
			String s = response.getBody();

			System.out.println("Send sms for " + mobile + " " + s);
		} catch(Exception ex) {
			System.out.println("Failed to Send sms for " + mobile );
		}
	}
	

}
