package com.task.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.StudentTask;

public interface StudentTaskRepository extends JpaRepository<StudentTask,Integer> {
	
	//List<StudentTask> findByStudentId(Integer studentId);
	
   

}
