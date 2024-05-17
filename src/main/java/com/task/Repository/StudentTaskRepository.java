package com.task.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.Student;
import com.task.model.StudentTask;

public interface StudentTaskRepository extends JpaRepository<StudentTask,Integer> {
	
	 Optional<StudentTask> findById(int id);
	
   

}
