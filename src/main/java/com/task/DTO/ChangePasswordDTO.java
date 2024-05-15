package com.task.DTO;



public class ChangePasswordDTO {
	
    private String password;

  
    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

