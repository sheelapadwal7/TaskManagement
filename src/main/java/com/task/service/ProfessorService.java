package com.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.DTO.LoginRequestDTO;
import com.task.Repository.ProfessorRepository;
import com.task.model.Professor;

@Service
public class ProfessorService {

	@Autowired
	ProfessorRepository professorRepository;


	
	/*
	 * public ProfessorService(ProfessorRepository professorRepository,
	 * BCryptPasswordEncoder passwordEncoder) {
	 * 
	 * this.professorRepository = professorRepository; this.passwordEncoder =
	 * passwordEncoder; }
	 */

	

	public Professor login(LoginRequestDTO loginRequestDto) {
		Optional<Professor> professors = professorRepository.findByUserName(loginRequestDto.getUserName());

		System.out.println(professors);
		Professor professor = null;

		if (professors.isPresent()) {

			Professor professorsdb = professors.get();

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//        	System.out.print("passwrod user: " + loginRequestDto.getPassword() + " from db:" + Professordb.getPassword());
			if (passwordEncoder.matches(loginRequestDto.getPassword(), professorsdb.getPassword())) {
				professor = professorsdb;
			}

		}

		return professor;
	}

	public List<Professor> getProfessor() {
		return professorRepository.findAll();

	}

	public Optional<Professor> getProfessorById(Integer id) {
		return professorRepository.findById(id);
	}

	
	public Professor addProfessor(Professor professor) {
		if (professor.getPassword() != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(professor.getPassword());
			professor.setPassword(hashedPassword);
		}
		return professorRepository.save(professor);
	}

	public Professor update(Integer id, Professor professor) {
		Optional<Professor> existingProfessorOptional = professorRepository.findById(id);
		if (existingProfessorOptional.isPresent()) {
			Professor existingProfessor = existingProfessorOptional.get();
			existingProfessor.setFirstName(professor.getFirstName());
			existingProfessor.setEmail(professor.getEmail());
			existingProfessor.setContactNumber(professor.getContactNumber());
			existingProfessor.setDob(professor.getDob());
			existingProfessor.setUserName(professor.getUserName());

			// Check if the password is being updated
			if (professor.getPassword() != null && !professor.getPassword().isEmpty()) {
				// Re-encrypt the password
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(professor.getPassword());
				existingProfessor.setPassword(hashedPassword);
			}

			return professorRepository.save(existingProfessor);
		} else {
			throw new IllegalArgumentException("Professor with id " + id + " does not exist");
		}
	}

	
	public boolean deleteProfessor(Integer id) {
		Optional<Professor> professorOptional = professorRepository.findById(id);
		if (professorOptional.isPresent()) {
			professorRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
