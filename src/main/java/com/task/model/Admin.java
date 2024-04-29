/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Integer id;
    
    
    @Column(name = "first_name", length = 128)
    private String firstName;
    
    
    @Column(name = "last_name", length = 128)
    private String lastName;
    
    
    @Column(name = "email", length = 64, nullable = false)
    private String email;
    
    
    @Column(name = "password", length = 64, nullable = false)
    private String password;
    
    @Column(name="user_Name")
    private String userName;
//    
//	@OneToMany(mappedBy = "admin")
//	private Task task;

//	public Task getTask() {
//		return task;
//	}
//
//	public void setTask(Task task) {
//		this.task = task;
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
 
	
    
}
