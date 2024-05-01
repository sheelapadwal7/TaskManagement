package com.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.Repository.UserRepository;
import com.task.model.User;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean isAdmin(String username, String password) {
        
        User user = userRepository.findByUsername(username);

        return user != null && password != null && user.getPassword().equals(password) && "admin".equals(user.getRole());
    }

}

