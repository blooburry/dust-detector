package com.example.dustdetector.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Async
    public void sendHighDustAlertEmail(String toAddress, int stofniveau) throws TimeoutException, InterruptedException, ExecutionException {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom("info@dustdetector.nl");
        msg.setTo(toAddress);
        msg.setSubject("Hoog stof niveau!");
        msg.setText("Uw stofniveau was gemeten als " + stofniveau + "/100. Dit is gevaarlijk.");

        this.logger.info("Bericht wordt gestuurd:");
        this.logger.info(msg.toString());

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            javaMailSender.send(msg);
        }, Executors.newFixedThreadPool(1));

        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            this.logger.error("Email sending failed: ", e);
            throw new RuntimeException("Internal Server Error", e);
        }
    }
}
