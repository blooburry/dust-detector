package com.example.dustdetector.service;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.dustdetector.model.Detector;
import com.example.dustdetector.model.DustLevel;
import com.example.dustdetector.model.User;
import com.example.dustdetector.repository.DetectorRepository;
import com.example.dustdetector.repository.DustLevelRepository;
import com.example.dustdetector.repository.UserRepository;
import com.example.dustdetector.security.CustomLogoutHandler;

@Service
public class DustLevelService {

    // Repository klassen om met de database te communiceren
    @Autowired
    private DustLevelRepository dustLevelRepository;
    @Autowired
    private DetectorRepository detectorRepository; 
    @Autowired
    private UserRepository userRepository;

    // Email en SMS services
    @Autowired
    private EmailService emailService;
    @Autowired
    private TwilioService twilioService;
    
    private static final Logger logger = LoggerFactory.getLogger(DustLevelService.class);

    public void saveDustLevel(int level, int detectorId) throws Exception {
        Detector detector = detectorRepository.findById(detectorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Geen detector met id: " + detectorId) );
        DustLevel dustLevel = new DustLevel(detector, level); // een DustLevel object wordt gecreeerd
        dustLevelRepository.save(dustLevel);

        this.logger.info("Meting is opgeslagen!");

        // Check of het boven de grenswaarden gaat
        if(dustLevel.getLevel() >= 80) {

            this.logger.info("Meting gaat boven de grenswaarde, email wordt gestuurd");

            User u = this.userRepository.findById(
                dustLevel
                    .getDetector()
                    .getUser()
                    .getId()
            ).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Geen gebruiker gevonden"
            ));
            
            emailService.sendHighDustAlertEmail(
                u.getEmail(),
                dustLevel.getLevel()
            );

            this.logger.info("Een SMS gaat worden verstuurd");
            twilioService.sendSms(
                u.getPhone(), 
                "Waarschuwing: hoog stofniveau!"
            );
        }
    }
}
