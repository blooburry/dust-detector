package com.example.dustdetector.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.dustdetector.dto.MetingResDTO;
import com.example.dustdetector.model.Detector;
import com.example.dustdetector.model.DustLevel;
import com.example.dustdetector.model.User;
import com.example.dustdetector.repository.DetectorRepository;
import com.example.dustdetector.repository.DustLevelRepository;
import com.example.dustdetector.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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

        // Websocket
        @Autowired
        private SimpMessagingTemplate template;

        private static final Logger logger = LoggerFactory.getLogger(DustLevelService.class);

        public void saveDustLevel(int level, int detectorId) throws Exception {
                Detector detector = detectorRepository.findById(detectorId).orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                "Geen detector met id: " + detectorId));
                DustLevel dustLevel = new DustLevel(detector, level); // een DustLevel object wordt gecreeerd
                User u = this.userRepository.findById(
                        dustLevel
                                        .getDetector()
                                        .getUser()
                                        .getId())
                        .orElseThrow(() -> new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST, "Geen gebruiker gevonden"));

                dustLevelRepository.save(dustLevel);

                this.logger.info("Meting is opgeslagen!");

                this.logger.info("Update word gestuurd over websocket...");
                try {
                        MetingResDTO update = new MetingResDTO(
                                dustLevel.getDate(), 
                                dustLevel.getLevel(), 
                                detectorId
                        );
                        ObjectMapper mapper = new ObjectMapper();
                        String jsonPayload = mapper.writeValueAsString(update);
                        this.template.convertAndSendToUser(u.getUsername(), "/metingen-updates", jsonPayload);
                        this.logger.info(jsonPayload);
                        this.logger.info("verzonden naar /user/" + u.getUsername() + "/metingen-updates");
                } catch (Exception e) {
                        this.logger.error("Update kon niet worden verzonden", e);
                }

                // Check of het boven de grenswaarden gaat
                if (dustLevel.getLevel() >= 80) {

                        this.logger.info("Een SMS gaat worden verstuurd");

                        twilioService.sendSms(
                                        u.getPhone(),
                                        "Waarschuwing: hoog stofniveau!");

                        this.logger.info("Een email gaat worden verstuurd");
                        emailService.sendHighDustAlertEmail(
                                        u.getEmail(),
                                        dustLevel.getLevel());
                }
        }

        public List<DustLevel> findDustLevelsByUserId(int userId) {
                return this.dustLevelRepository.findAllByUserId(userId);
        }
}
