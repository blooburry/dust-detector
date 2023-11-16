package com.example.dustdetector.controller;

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
import com.example.dustdetector.repository.UserRepository;

import javax.validation.Valid;

@Controller
public class SignUpController {

    @GetMapping("/signup")
    public String signup() {
        return "/signup";
    }
}

@RestController
@RequestMapping("/api")
class SignUpRestController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PingController.class);    

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDTO user) {


        this.logger.info("User registered successfully.");   
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
}
