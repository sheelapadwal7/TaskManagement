package com.task.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.DTO.LoginRequestDTO;
import com.task.Repository.AdminRepository;
import com.task.model.Admin;



@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;
	
	

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

	// Delete Admin by id
	public boolean deleteStudent(Integer id) {
		boolean exists = adminRepository.existsById(id);
		if (exists) {
			adminRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
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
