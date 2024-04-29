package com.task.DTO;

public class LoginResponseDTO

{

	private boolean status;
	private String message;

	private UserDTO user;

	private String token;

	public LoginResponseDTO(boolean status, String message, String token) {
		super();
		this.status = status;
		this.message = message;
		this.token = token;
	}

	public LoginResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
