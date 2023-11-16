package com.example.dustdetector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dustdetector.model.User;
import com.example.dustdetector.repository.UserRepository;

@Service
public class UserService {

    @Autowired // Dependency injection
    private UserRepository userRepository;

    public void createUser(String username, String password, String phoneNumber, String email) {
        userRepository.createUser(
            username,
            password,
            phoneNumber,
            email,
            1, // 1 voor klant
            null // 2FA key is aanvankelijk NULL
        );
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
