package com.task.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	Optional<Student> findByUserName(String userName);

	List<Student> findByAccountStatus(String accountStatus);

	Optional<Student> findByEmail(String email);
	
	 Optional<Student> findById(int id);

	
}
