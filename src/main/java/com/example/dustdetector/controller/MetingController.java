package com.example.dustdetector.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.dustdetector.dto.MetingDTO;
import com.example.dustdetector.model.DustLevel;
import com.example.dustdetector.service.DustLevelService;
import com.example.dustdetector.service.EmailService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;

@RestController
public class MetingController {

    private final Bucket bucket;
    
    private static final Logger logger = LoggerFactory.getLogger(MetingController.class);

    @Autowired
    private DustLevelService dustLevelService;

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
        @RequestHeader(value = "key", required = false) String requestKey
    ) {
        this.logger.info("Meting ontvangen");

        if(!bucket.tryConsume(1)) return new ResponseEntity<>("Teveel requests!", HttpStatus.TOO_MANY_REQUESTS);

        this.logger.info("API key aan het checken...");

            if (requestKey == null || !requestKey.equals(apiKey)) {
                return new ResponseEntity<>("Ongeldige API key", HttpStatus.UNAUTHORIZED);
            }

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
