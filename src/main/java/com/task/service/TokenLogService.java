package com.task.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.Repository.TokenLogRepository;
import com.task.enums.LinkType;
import com.task.model.TokenLog;

@Service
public class TokenLogService {

	@Autowired
	TokenLogRepository tokenLogRepository;

	/*
	 * public boolean verifyToken(String token) {
	 * 
	 * Optional<TokenLog> tokenLog = tokenLogRepository.findFirstByToken(token);
	 * 
	 * if (tokenLog.isPresent()) { TokenLog log = tokenLog.get(); return
	 * log.isValid(); }
	 * 
	 * return false; }
	 */

	public boolean verifyToken(String token) {
		// Retrieve the token log
		Optional<TokenLog> tokenLogOptional = tokenLogRepository.findByToken(token);

		if (tokenLogOptional.isPresent()) {
			TokenLog log = tokenLogOptional.get();
			if (log.isValid()) {

				LocalDateTime expiryTime = log.getExpiryTime();
				return expiryTime != null && !expiryTime.isBefore(LocalDateTime.now());
			}
		}

		return false;
	}
	
	public boolean isValidToken(String token) {
		Optional<TokenLog> tokenLogOptional = tokenLogRepository.findByToken(token);

		if (tokenLogOptional.isPresent()) {
			TokenLog tokenLog = tokenLogOptional.get();
			if (tokenLog.isValid()) {
				LocalDateTime expiryTime = tokenLog.getExpiryTime();
				// Check if the token is not expired
				return !isTokenExpired(expiryTime);
			}
		}
		// Token not found or invalid
		return false;
	}
	
	

	private boolean isTokenExpired(LocalDateTime expiryTime) {
		return expiryTime != null && expiryTime.isBefore(LocalDateTime.now());
	}


	public String generateToken(int studentId, String email) {
		String token = UUID.randomUUID().toString();

		LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(1);

		TokenLog tokenLog = new TokenLog();
		tokenLog.setToken(token);
		tokenLog.setValid(true);
		tokenLog.setExpiryTime(expiryTime);

		addLogForStudentLogin(token, studentId, email, expiryTime);

		return token;
	}

	public TokenLog addLogForStudentLogin(String token, int studentId, String email, LocalDateTime expiryTime) {
		TokenLog tl = new TokenLog();
		tl.setLinkId(studentId);
		tl.setLinkType(LinkType.STUDENT);
		tl.setToken(token);
		tl.setValid(true);
		tl.setCreatedAt(LocalDateTime.now());
		tl.setUserName(email);
		tl.setExpiryTime(expiryTime);
		return tokenLogRepository.save(tl);
	}

	public List<TokenLog> getTokenLog() {

		return (List<TokenLog>) tokenLogRepository.findAll();

	}

	public Optional<TokenLog> getTokenLogById(Integer id) {
		return tokenLogRepository.findById(id);

	}

	public List<String> validate(TokenLog tokenLog) {

		List<String> error = new ArrayList<>();

		if (tokenLog.getUserName() == null) {
			error.add("TokenLog Username can not be empty");
		}

		if (tokenLog.getToken() == null) {
			error.add("Token can not be empty");
		}

//		if (tokenLog.getPurpose() == null) {
//			error.add("Purpose can not be empty");
//		}

		if (tokenLog.getLinkType() == null) {
			error.add("LinkType can not be empty");
		}
		if (tokenLog.getIp() == null) {
			error.add("IP can not be empty");
		}

		if (tokenLog.getLinkId() == 0) {
			error.add("LinkId can not be empty");
		}

		if (tokenLog.getAttempt() == 0) {
			error.add("Attempt can not be empty");
		}

		return error;
	}

	public TokenLog updateTokenLog(Integer id, TokenLog tokenLog) {
		TokenLog existingStaff = tokenLogRepository.findById(id).orElse(null);
		existingStaff.setUserName(tokenLog.getUserName());
		existingStaff.setToken(tokenLog.getToken());
//		existingStaff.setPurpose(tokenLog.getPurpose());
		existingStaff.setLinkType(tokenLog.getLinkType());
		existingStaff.setIp(tokenLog.getIp());
		existingStaff.setLinkId(tokenLog.getLinkId());
		existingStaff.setAttempt(tokenLog.getAttempt());
		return tokenLogRepository.save(existingStaff);
	}

	public boolean deleteTokenLog(Integer id) {

		boolean exists = tokenLogRepository.existsById(id);
		if (exists) {
			tokenLogRepository.deleteById(id);
			return true;
		} else {

			return false;
		}

	}


	public boolean invalidateToken(String token) {
		Optional<TokenLog> tokenLogOptional = tokenLogRepository.findByToken(token);

		if (tokenLogOptional.isPresent()) {
			TokenLog log = tokenLogOptional.get();
			log.setValid(false);
			log.setExpiryTime(LocalDateTime.now().plusMinutes(1));
			log.setLogout(LocalDateTime.now());
			tokenLogRepository.save(log);
			return true;
		}
		return false;
	}

}
