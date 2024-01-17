package com.example.dustdetector.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.dustdetector.dto.MetingDTO;
import com.example.dustdetector.dto.MetingResDTO;
import com.example.dustdetector.model.DustLevel;
import com.example.dustdetector.model.User;
import com.example.dustdetector.service.DustLevelService;
import com.example.dustdetector.service.EmailService;
import com.example.dustdetector.service.UserService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class MetingController {

    private final Bucket bucket;

    private static final Logger logger = LoggerFactory.getLogger(MetingController.class);

    @Autowired
    private DustLevelService dustLevelService;

    @Autowired
    private UserService userService;

    @Value("${api.key}")
    private String apiKey;

    public MetingController() {
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping("/api/meting")
    public ResponseEntity<String> ontvangMeting(
            @Valid @RequestBody MetingDTO meting,
            @RequestHeader(value = "key", required = false) String requestKey) {
        this.logger.info("Meting ontvangen");

        if (!bucket.tryConsume(1))
            return new ResponseEntity<>("Teveel requests!", HttpStatus.TOO_MANY_REQUESTS);

        this.logger.info("API key aan het checken...");

        if (requestKey == null || !requestKey.equals(apiKey)) {
            return new ResponseEntity<>("Ongeldige API key", HttpStatus.UNAUTHORIZED);
        }

        try {
            dustLevelService.saveDustLevel(meting.getStofniveau(), meting.getDetectorId());

            return new ResponseEntity<>("Meting successvol opgeslagen", HttpStatus.CREATED);
        } catch (ResponseStatusException r) {
            HttpStatusCode status = r.getStatusCode();
            return new ResponseEntity<>(r.getMessage(), status);
        } catch (Exception e) {
            return new ResponseEntity<>("De meting kon niet worden opgeslagen: " + e.getCause(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/meting")
    public ResponseEntity<?> getPreviousMeasurements(@AuthenticationPrincipal UserDetails u) {
                
        if (u == null) return new ResponseEntity<>("De gebruiker is nog niet ingelogd", HttpStatus.UNAUTHORIZED);
                
        this.logger.info("Request ontvangen voor oude metingen van " + u.getUsername());

        Optional<User> optUser = this.userService.findUserByUsername(u.getUsername());
        User user;
        if (optUser.isEmpty()) {
            return new ResponseEntity<>("Geen gebruiker met id " + u.getUsername(), HttpStatus.NOT_FOUND);
        } else {
            user = optUser.get();
        }

        List<MetingResDTO> res = this.dustLevelService.findDustLevelsByUserId(user.getId())
            .stream()
            .map(m -> new MetingResDTO(m.getDate(), m.getLevel(), m.getDetector().getId()))
            .collect(Collectors.toList());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
