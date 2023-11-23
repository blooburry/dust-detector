package com.example.dustdetector.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dustdetector.dto.SignUpDTO;
import com.example.dustdetector.model.User;
import com.example.dustdetector.service.UserService;

import jakarta.validation.Valid;


@Controller
public class SignUpController {

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}

@RestController
@RequestMapping("/api")
class SignUpRestController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDTO user) {

        this.logger.info("Received signup request");
        this.logger.info(user.toString());

        try {
            // Validate if the user with the given email already exists
            Optional<User> conflictingUser = userService.findUserByEmail(user.getEmail());
            if (conflictingUser.isPresent()) {
                this.logger.error("User with the provided email already exists");
                return new ResponseEntity<>("User with the provided email already exists", HttpStatus.CONFLICT);
            }

            // Have the userService create a new user
            userService.createUser(user.getUsername(), user.getPassword(), user.getPhoneNumber(), user.getEmail());

            // Stuur succesvol 201 Response
            this.logger.info("User registered successfully.");
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

        } catch (Exception e) {
            // Stuur foutmelding 500 Response
            this.logger.error("Error during user registration", e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
