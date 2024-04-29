package com.task.DTO;

import java.util.Date;

import com.task.enums.Gender;



public class UserDTO {

	private Integer id;
	
	private String userName;

	private String firstName;

	private String middleName;

	private String lastName;

	private String email;

	private String contactNumber;

	private Gender gender;

	private Date date;

	private String collegeName;

	private Integer rollNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public Integer getRollNo() {
		return rollNo;
	}

	public void setRollNo(Integer rollNo) {
		this.rollNo = rollNo;
	}

	public UserDTO(Integer id, String userName, String firstName, String middleName, String lastName, String email,
			String contactNumber, Gender gender, Date date, String collegeName, Integer rollNo) {
		super();
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.contactNumber = contactNumber;
		this.gender = gender;
		this.date = date;
		this.collegeName = collegeName;
		this.rollNo = rollNo;
	}

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}

	
	