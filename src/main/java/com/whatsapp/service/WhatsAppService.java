package com.whatsapp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class WhatsAppService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String fromNumber; // approved production WhatsApp sender

    @Value("${twilio.whatsapp.template.sid}")
    private String welcomeTemplateSid; // approved content/template SID

    private void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public void sendInitialTemplate(String phone, String name) {
        initTwilio();

        // Twilio expects JSON string for variables.
        // The key numbers/letters must match the template placeholders.
        String contentVariables = String.format("{\"1\":\"%s\"}", name);

        Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+" + phone),
                new com.twilio.type.PhoneNumber(fromNumber),
                ""
        )
        .setContentSid(welcomeTemplateSid)
        .setContentVariables(contentVariables)
        .create();
    }

    public void sendFreeFormMessage(String phone, String text) {
        initTwilio();

        Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+" + phone),
                new com.twilio.type.PhoneNumber(fromNumber),
                text
        ).create();
    }
}