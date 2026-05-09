package com.tutoring.StudentTutoringService.service;

import com.tutoring.StudentTutoringService.model.UserInfo;
import com.tutoring.StudentTutoringService.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfo login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public void register(String name, String email, String password, String role) {
        userRepository.insertUser(name, email, password, role);
    }
}