package com.task.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.task.model.Student;
import com.task.model.StudentTask;
import com.task.model.Task;

public interface StudentTaskRepository extends JpaRepository<StudentTask,Integer> {
	
	//List<StudentTask> findByStudentId(Integer studentId);
	
   

}
