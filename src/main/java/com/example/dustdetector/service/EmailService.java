package com.example.dustdetector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendHighDustAlertEmail(String toAddress, int stofniveau){
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(toAddress);
        msg.setSubject("Hoog stof niveau!");
        msg.setText("Uw stofniveau was gemeten als " + stofniveau + "/100. Dit is gevaarlijk.");
        javaMailSender.send(msg);
    }
}
