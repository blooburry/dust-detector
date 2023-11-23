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
public class SignInController {

    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }
}

@RestController
@RequestMapping("/api")
class SignUpRestController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    // @PostMapping("/signin")
    // public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDTO user) {

    // }
}
