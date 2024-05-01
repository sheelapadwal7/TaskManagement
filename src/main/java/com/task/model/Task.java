package com.task.model;

import java.util.Date;
import java.util.List;

import com.task.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="task")
public class Task {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Integer id;
	
	@Column(name = "task_name")
	private String name;
	
	@Column(name = "task_desc")
	private String description;
	
	@Column(name = "completion_Date")
	private Date completionDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	
	@Column(name = "actual_completion_Date")
	private Date actualCompletionDate;
	
	
	@Column(name="incompletion_reason")
	private String inCompletionReason;

	@ManyToMany(mappedBy = "tasks")
    private List<Student> student;

	

	public String getInCompletionReason() {
		return inCompletionReason;
	}

	public void setInCompletionReason(String inCompletionReason) {
		this.inCompletionReason = inCompletionReason;
	}

	public List<Student> getStudent() {
		return student;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getActualCompletionDate() {
		return actualCompletionDate;
	}

	public void setActualCompletionDate(Date actualCompletionDate) {
		this.actualCompletionDate = actualCompletionDate;
	}
	
	//@ManyToMany
	//@JoinColumn(name = "student_id")
	//private Student student;
	
	//@ManyToOne
	//@JoinColumn(name = "admin")
	//private Admin admin;
	

	
	
}
