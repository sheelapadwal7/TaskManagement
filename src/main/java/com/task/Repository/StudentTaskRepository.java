package com.task.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.StudentTask;

public interface StudentTaskRepository extends JpaRepository<StudentTask,Integer> {
	
	List<StudentTask> findByStudentId(Integer studentId);

}
