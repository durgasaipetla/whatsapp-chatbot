package com.whatsapp.controller;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.twiml.fax.Receive.MediaType;

@RestController
@RequestMapping("/api/twilio")
public class TwilioStatusController {

    private final Map<String, String> messageStatusStore = new ConcurrentHashMap<>();

    @PostMapping(value = "/status",          
    	consumes = "application/x-www-form-urlencoded")

    public String handleStatusCallback(
            @RequestParam(value = "MessageSid", required = false) String messageSid,
            @RequestParam(value = "MessageStatus", required = false) String messageStatus,
            @RequestParam(value = "ErrorCode", required = false) String errorCode,
            @RequestParam(value = "To", required = false) String to,
            @RequestParam(value = "From", required = false) String from) {

        if (messageSid != null && messageStatus != null) {
            messageStatusStore.put(messageSid, messageStatus);
        }

        System.out.println(
                "Status callback -> SID=" + messageSid +
                ", Status=" + messageStatus +
                ", ErrorCode=" + errorCode +
                ", To=" + to +
                ", From=" + from
        );

        return "OK";
    }

    @GetMapping("/status/{sid}")
    public String getStatus(@PathVariable String sid) {
        return messageStatusStore.getOrDefault(sid, "UNKNOWN");
    }
}