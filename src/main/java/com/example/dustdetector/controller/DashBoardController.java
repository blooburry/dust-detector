package com.example.dustdetector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DashBoardController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}

@RestController
class DashBoardRestController {
}