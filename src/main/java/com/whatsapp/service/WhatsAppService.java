package com.whatsapp.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;

@Service
public class WhatsAppService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    public void sendMessage(String phone, String text) {

        Twilio.init(accountSid, authToken);

        com.twilio.rest.api.v2010.account.Message message =
            com.twilio.rest.api.v2010.account.Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+" + phone),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                text
            ).create();

        System.out.println("Message SID: " + message.getSid());
        System.out.println("Status: " + message.getStatus());
    }
}

