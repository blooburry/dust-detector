package com.example.dustdetector.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
public class PingController {
    
    private static final Logger logger = LoggerFactory.getLogger(PingController.class);

    @GetMapping("/api/ping")
    public String ping() {
        this.logger.info("Received a ping request!");
        return "Pinged Dust Detector v0.0!";
    }
}
