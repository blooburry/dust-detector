package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {

    @GetMapping("/data")
    public Map<String, String> fetchDataFromServer() {
        // Simuleer het ophalen van gegevens van de server
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", "Dit is een serverreactie!");
        return responseData;
    }
}