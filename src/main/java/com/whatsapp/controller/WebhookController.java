package com.whatsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.twilio.twiml.messaging.Message;

import com.twilio.twiml.MessagingResponse;
import com.whatsapp.service.ChatService;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public String receiveMessage(
            @RequestParam("From") String phone,
            @RequestParam("Body") String message,
            @RequestParam(value = "MediaUrl0", required = false) String mediaUrl) {

        String reply = chatService.processMessage(phone, message, mediaUrl);

        MessagingResponse twiml = new MessagingResponse.Builder()
                .message(new Message.Builder(reply).build())
                .build();

        return twiml.toXml();
    }
}