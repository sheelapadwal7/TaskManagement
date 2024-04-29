package com.task.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.model.TokenLog;

/**
 * 
 * 
 @author DURGESH */

@Repository
public interface TokenLogRepository extends JpaRepository<TokenLog , Integer> {

	Optional<TokenLog> findByToken(String token);

	






}
