package com.example.dustdetector.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dustdetector.model.User;
import com.example.dustdetector.repository.UserRepository;

@Service
public class UserService {

    @Autowired // Dependency injection
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(String username, String password, String phoneNumber, String email) {
        String hash = passwordEncoder.encode(password);
        User u = new User(0, username, hash, phoneNumber, email, 1, null);
        userRepository.save(u);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
