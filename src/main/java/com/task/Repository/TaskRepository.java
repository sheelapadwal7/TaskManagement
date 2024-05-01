package com.task.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.task.DTO.StudentTaskDTO;
import com.task.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	@Query(value = "SELECT t.task_id AS taskId, t.task_name AS taskName, t.task_desc AS taskDescription, st.id AS studentTaskId "
			+ "FROM Task t LEFT JOIN StudentTask st ON st.task_id = t.task_id "
			+ "WHERE t.task_id = :taskId", nativeQuery = true)
	List<StudentTaskDTO> findTaskWithStudentTasks(@Param("taskId") Integer taskId);

}
