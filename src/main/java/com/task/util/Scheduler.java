package com.task.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.task.Repository.StudentRepository;
import com.task.model.Student;

@Component
public class Scheduler {

	@Autowired
	private StudentRepository studentRepository;

	@Scheduled(cron = "0 * * * * ?") // Run every minute
	public void scheduleTask() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		String strDate = dateFormat.format(new Date());
		System.out.println("Cron job Scheduler: Job running at - " + strDate);

		List<Student> lockedStudents = studentRepository.findByAccountStatus("LOCKED");

		for (Student student : lockedStudents) {

			student.setAccountStatus("UNLOCK");
			student.setLoginAttempts(0);
			student.setLockedDateTime(null);
			studentRepository.save(student);

			sendSMS(student.getContactNumber(), "Your account has been unlocked. Please login.");
		}
	}

	private void sendSMS(String mobile, String message) {
		System.out.println("Sending SMS to " + mobile + ": " + message);
	}
}
