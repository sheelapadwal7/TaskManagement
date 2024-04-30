package com.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.DTO.LoginRequestDTO;
import com.task.Repository.ProfessorRepository;
import com.task.model.Professor;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Professor login(LoginRequestDTO loginRequestDto) {
        // Find professor by username
        Optional<Professor> professorOptional = professorRepository.findByUserName(loginRequestDto.getUserName());

        if (professorOptional.isPresent()) {
            Professor professor = professorOptional.get();

            // Check if the password matches
            if (bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), professor.getPassword())) {
                return professor;
            }
        }

        return null; // Return null if login fails
    }

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public Optional<Professor> getProfessorById(Integer id) {
        return professorRepository.findById(id);
    }

    @Transactional
    public Professor addProfessor(Professor professor) {
        // Encrypt password before saving
        String hashedPassword = bCryptPasswordEncoder.encode(professor.getPassword());
        professor.setPassword(hashedPassword);
        return professorRepository.save(professor);
    }

    @Transactional
    public Professor updateProfessor(Integer id, Professor professor) {
        Optional<Professor> existingProfessorOptional = professorRepository.findById(id);
        if (existingProfessorOptional.isPresent()) {
            Professor existingProfessor = existingProfessorOptional.get();
            // Update existing professor's fields
            existingProfessor.setFirstName(professor.getFirstName());
            existingProfessor.setEmail(professor.getEmail());
            existingProfessor.setContactNumber(professor.getContactNumber());
            existingProfessor.setDob(professor.getDob());
            existingProfessor.setUserName(professor.getUserName());

            // Check if the password is being updated
            if (professor.getPassword() != null && !professor.getPassword().isEmpty()) {
                // Re-encrypt the password
                String hashedPassword = bCryptPasswordEncoder.encode(professor.getPassword());
                existingProfessor.setPassword(hashedPassword);
            }

            return professorRepository.save(existingProfessor);
        } else {
            throw new IllegalArgumentException("Professor with id " + id + " does not exist");
        }
    }

    @Transactional
    public boolean deleteProfessor(Integer id) {
        if (professorRepository.existsById(id)) {
            professorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
