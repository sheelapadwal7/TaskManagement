package com.task.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

}
