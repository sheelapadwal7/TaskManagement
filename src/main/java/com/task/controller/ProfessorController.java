package com.task.controller;

import com.task.DTO.LoginRequestDTO;
import com.task.model.Professor;
import com.task.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
		this.professorService = professorService;
	}

	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDto) {
        Professor professor = professorService.login(loginRequestDto);
        if (professor != null) {
            return ResponseEntity.ok(professor);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping
    public List<Professor> getAllProfessors() {
        return professorService.getProfessor();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfessorById(@PathVariable("id") Integer id) {
        Optional<Professor> professor = professorService.getProfessorById(id);
        if (professor.isPresent()) {
            return ResponseEntity.ok(professor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Professor> addProfessor(@RequestBody Professor professor) {
        Professor addedProfessor = professorService.addProfessor(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProfessor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfessor(@PathVariable("id") Integer id, @RequestBody Professor professor) {
        try {
            Professor updatedProfessor = professorService.update(id, professor);
            return ResponseEntity.ok(updatedProfessor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable("id") Integer id) {
        if (professorService.deleteProfessor(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
