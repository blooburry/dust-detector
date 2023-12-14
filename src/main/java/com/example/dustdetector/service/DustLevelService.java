package com.example.dustdetector.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dustdetector.model.DustLevel;
import com.example.dustdetector.repository.DustLevelRepository;

@Service
public class DustLevelService {

    @Autowired
    private DustLevelRepository dustLevelRepository;
    @Autowired
    private EmailService emailService;

    public void saveDustLevel(int level, int detectorId) {
        DustLevel dustLevel = new DustLevel(detectorId, level); // een DustLevel object wordt gecreeerd
        dustLevelRepository.save(dustLevel);

        // Check of het boven de grenswaarden gaat
        if(dustLevel.getLevel() >= 80) {
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
