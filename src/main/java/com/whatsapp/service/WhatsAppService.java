
package com.whatsapp.service;

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
    private String fromNumber;

    @Value("${twilio.whatsapp.template.sid}")
    private String welcomeTemplateSid;

    @Value("${twilio.status.callback.url}")
    private String statusCallbackUrl;

    private void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public void sendInitialTemplate(String phone, String name) {
        initTwilio();

        String contentVariables = String.format("{\"1\":\"%s\"}", name);

        Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+" + phone),
                new com.twilio.type.PhoneNumber(fromNumber),
                ""
        )
        .setContentSid(welcomeTemplateSid)
        .setContentVariables(contentVariables)
        .setStatusCallback(statusCallbackUrl)
        .create();
    }

    public void sendFreeFormMessage(String phone, String text) {

        initTwilio();

        Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+" + phone),
                new com.twilio.type.PhoneNumber(fromNumber),
                text
        )
        .setStatusCallback(statusCallbackUrl)
        .create();
    }
    
}