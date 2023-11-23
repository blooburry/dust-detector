package com.example.dustdetector.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dustdetector.model.User;
import com.example.dustdetector.repository.UserRepository;

@Service
public class UserService {

    @Autowired // Dependency injection
    private UserRepository userRepository;

    public void createUser(String username, String password, String phoneNumber, String email) {
        User u = new User(0, username, password, phoneNumber, email, 1, email);
        userRepository.save(u);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
