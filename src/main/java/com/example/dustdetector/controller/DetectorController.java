package com.example.dustdetector.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.dustdetector.dto.DetectorsResDTO;
import com.example.dustdetector.model.Detector;
import com.example.dustdetector.model.User;
import com.example.dustdetector.service.DetectorService;
import com.example.dustdetector.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// Deze klas luistert naar HTTP Requests op /api/user/{userId}/detector, 
// en geeft informatie terug over de detectors
@RestController
public class DetectorController {

    @Autowired
    private DetectorService detectorService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(DetectorController.class);

    @GetMapping("/api/detectors")
    public ResponseEntity<?> getUserDetectors(@AuthenticationPrincipal UserDetails u) {

        if (u == null) {
            return new ResponseEntity<>("De gebruiker is nog niet ingelogd", HttpStatus.UNAUTHORIZED);
        }

        Optional<User> optUser = this.userService.findUserByUsername(u.getUsername());

        User user;

        if (optUser.isEmpty()) {
            return new ResponseEntity<>("Geen gebruiker met id " + u.getUsername(), HttpStatus.NOT_FOUND);
        } else {
            user = optUser.get();
        }

        this.logger.info("Detectors gevraagd voor gebruiker" + user.getUsername());

        List<DetectorsResDTO> res;

        try {
            res = detectorService.findDetectorsByUserId(user.getId()).stream().map(
                (d) -> new DetectorsResDTO(d.getIpAddress(), d.getDateBought(), d.getStatus())
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return new ResponseEntity<>(
                "De database kon de query niet vervullen: " + e.getMessage() 
                + "\n vanwege: " + e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
