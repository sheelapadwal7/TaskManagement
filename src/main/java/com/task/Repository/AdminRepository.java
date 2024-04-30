/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.task.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.task.model.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Optional<Admin> findByUserName(String userName);

	//@Query(value = "select t.*, st.* from task t left join student_tasks st on st.tasks_task_id = t.task_id where t.task_id = ?")
	//CustomDto findFirst(Integer taskId);
    
}
