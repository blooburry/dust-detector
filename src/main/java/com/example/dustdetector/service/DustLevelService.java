package com.example.dustdetector.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.dustdetector.model.Detector;
import com.example.dustdetector.model.DustLevel;
import com.example.dustdetector.repository.DetectorRepository;
import com.example.dustdetector.repository.DustLevelRepository;
import com.example.dustdetector.security.CustomLogoutHandler;

@Service
public class DustLevelService {

    @Autowired
    private DustLevelRepository dustLevelRepository;
    @Autowired
    private DetectorRepository detectorRepository;
    @Autowired
    private EmailService emailService;
    
    private static final Logger logger = LoggerFactory.getLogger(DustLevelService.class);

    public void saveDustLevel(int level, int detectorId) {
        Detector detector = detectorRepository.findById(detectorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Geen detector met id: " + detectorId) );
        DustLevel dustLevel = new DustLevel(detector, level); // een DustLevel object wordt gecreeerd
        dustLevelRepository.save(dustLevel);

        this.logger.info("Meting is opgeslagen!");

        // Check of het boven de grenswaarden gaat
        if(dustLevel.getLevel() >= 80) {

            this.logger.info("Meting gaat boven de grenswaarde, email wordt gestuurd");
            emailService.sendHighDustAlertEmail(
                dustLevel
                    .getDetector() // van welke detector kwam deze meting?
                    .getUser()     // wie is de eigenaar van deze detector?
                    .getEmail(),   // wat is hun email?
                dustLevel.getLevel()
            );
        }
    }
}
