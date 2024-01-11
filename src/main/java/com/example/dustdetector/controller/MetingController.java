package com.example.dustdetector.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.dustdetector.dto.MetingDTO;
import com.example.dustdetector.model.DustLevel;
import com.example.dustdetector.service.DustLevelService;
import com.example.dustdetector.service.EmailService;

import jakarta.validation.Valid;

@RestController
public class MetingController {
    
    private static final Logger logger = LoggerFactory.getLogger(MetingController.class);

    @Autowired
    private DustLevelService dustLevelService;
    
    @PostMapping("/api/meting")
    public ResponseEntity<String> ontvangMeting(@Valid @RequestBody MetingDTO meting) {
        this.logger.info("Meting ontvangen");

        try {
            dustLevelService.saveDustLevel(meting.getStofniveau(), meting.getDetectorId());
            return new ResponseEntity<>("Meting successvol opgeslagen", HttpStatus.CREATED);

        } catch (ResponseStatusException r){
            HttpStatusCode status = r.getStatusCode();
            return new ResponseEntity<>(r.getMessage(), status);
        } catch (Exception e) {
            return new ResponseEntity<>("De meting kon niet worden opgeslagen: " + e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
