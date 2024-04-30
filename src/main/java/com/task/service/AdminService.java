package com.task.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.DTO.LoginRequestDTO;
import com.task.Repository.AdminRepository;
import com.task.Repository.TaskRepository;
import com.task.model.Admin;
import com.task.model.Task;



@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	

	public Admin login(LoginRequestDTO loginRequestDto) {
		System.out.println("3");
		Optional<Admin> admino = adminRepository.findByUserName(loginRequestDto.getUserName());

		System.out.println(admino);
		Admin admin = null;

		if (admino.isPresent()) {

			Admin admindb = admino.get();

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//        	System.out.print("passwrod user: " + loginRequestDto.getPassword() + " from db:" + studentdb.getPassword());
			if (passwordEncoder.matches(loginRequestDto.getPassword(), admindb.getPassword())) {
				admin = admindb;
			}

		}
		System.out.println("4");
		return admin;
	}

	// Create Admin
	public Admin AddAdmin(Admin admin) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		String hashcode = bCryptPasswordEncoder.encode(admin.getPassword());
		admin.setPassword(hashcode);
		admin = adminRepository.save(admin);
		return admin;
	}

	// Get Admin
	public List<Admin> getAdmin() {

		return adminRepository.findAll();
	}

	// Get Admin by Id
	public Optional<Admin> getAdminById(Integer adminId) {

		return adminRepository.findById(adminId);

	}




    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
    
    public void deleteStudent(Integer id) {
        taskRepository.deleteById(id);
    }
    
    public void deleteProfessor(Integer id) {
        taskRepository.deleteById(id);
    }
	

	// update admin by id
	public Admin updateAdmin(int requirementid, Admin admin) {

		Admin existingAdmin = adminRepository.findById(requirementid).orElse(null);

		existingAdmin.setId(admin.getId());
		existingAdmin.setEmail(admin.getEmail());
		existingAdmin.setFirstName(admin.getFirstName());
		existingAdmin.setLastName(admin.getLastName());
		existingAdmin.setPassword(admin.getPassword());
		
		
		return adminRepository.save(existingAdmin);
		
	}


}
