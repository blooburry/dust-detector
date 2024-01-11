package com.example.dustdetector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.dustdetector.controller.PingController;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class TwilioService {
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    private static final Logger logger = LoggerFactory.getLogger(TwilioService.class);

    public void sendSms(String toPhoneNumber, String messageBody) throws Exception {
        this.logger.info("Twilio init... ");
        Twilio.init(accountSid, authToken);

        this.logger.info("Twilio init successful");

        try {
            Message.creator(
                    new PhoneNumber("+" + toPhoneNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    messageBody
            ).create();
        } catch (Exception e) {
            this.logger.error("SMS sending failed: ", e);
            throw e;
        }

        this.logger.info("SMS is successvol verstuurd!");
    }
}
