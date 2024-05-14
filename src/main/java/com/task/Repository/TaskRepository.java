package com.task.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.task.DTO.StudentTaskDTO;
import com.task.enums.SortCriteria;
import com.task.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	@Query(value = "SELECT t.task_id AS taskId, t.task_name AS taskName, t.task_desc AS taskDescription, st.id AS studentTaskId "
			+ "FROM task t LEFT JOIN studenttask st ON st.task_id = t.task_id "
			+ "WHERE t.task_id = :taskId", nativeQuery = true)
	List<Object[]> findTaskWithStudentTasks(@Param("taskId") Integer taskId);

	@Query("SELECT NEW com.task.DTO.StudentTaskDTO(t.id, t.name, t.description, st.id) FROM Task t left join StudentTask st on t.id = st.task.id WHERE t.id = :taskId")
	List<StudentTaskDTO> findTaskWithStudentTasksJQL(@Param("taskId") Integer taskId);

	// query for pagination

	@Query(value = "SELECT * FROM task LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Task> findAllTasksWithPagination(@Param("offset") int offset, @Param("limit") int limit);

	@Query(value = "SELECT * FROM task " + "ORDER BY " + "CASE :sortColumn "
			+ "    WHEN 'COMPLETION_DATE' THEN completion_date "
			+ "    WHEN 'ACTUAL_COMPLETION_DATE' THEN actual_completion_date " + "    ELSE default_column " + "END "
			+ "LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Task> findAllTasksWithPaginationAndSorting(@Param("offset") int offset, @Param("limit") int limit,
			@Param("sortColumn") SortCriteria sortCriteria);

	@Query(value = "SELECT * FROM task " + "ORDER BY " + "CASE :sortColumn "
			+ "    WHEN 'COMPLETION_DATE' THEN completion_date "
			+ "    WHEN 'ACTUAL_COMPLETION_DATE' THEN actual_completion_date " + " " + "END Desc "
			+ "LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Task> findAllTasksWithPaginationAndSortingByDirection(@Param("offset") int offset, @Param("limit") int limit,
			@Param("sortColumn") SortCriteria sortCriteria);

	@Query(value = "SELECT * FROM task", nativeQuery = true)
	Page<Task> findAllTasksWithPagination(Pageable pageable);
	

	Page<Task> findAll(Pageable pageable);

	Page<Task> findAll(Specification<Task> specs, Pageable pageable);
}
