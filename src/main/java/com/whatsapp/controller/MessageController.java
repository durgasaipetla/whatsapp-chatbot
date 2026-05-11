package com.whatsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.entity.ChatStep;
import com.whatsapp.entity.Contact;
import com.whatsapp.service.ExcelService;
import com.whatsapp.service.SessionService;
import com.whatsapp.service.WhatsAppService;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private WhatsAppService whatsappService;

    @Autowired
    private SessionService sessionService;

    @Value("${excel.source}")
    private String excelSource;

    @GetMapping("/send")
    public String sendMessages() {
        List<Contact> contacts = excelService.readExcel(excelSource);

        for (Contact c : contacts) {
            if (!c.isConsent()) {
                continue;
            }

            String phone = normalizePhone(c.getPhone());
            if (phone == null) {
                continue;
            }

            sessionService.setStep(phone, ChatStep.START);

            // First outreach must be an approved template in production.
            whatsappService.sendInitialTemplate(phone, c.getName());
        }

        return "Messages Sent Successfully!";
    }

    private String normalizePhone(String phone) {
        if (phone == null) return null;

        String cleaned = phone.replaceAll("\\D", "");
        if (cleaned.isEmpty()) return null;

        // India example; adjust if your candidate list includes other countries.
        if (!cleaned.startsWith("91")) {
            cleaned = "91" + cleaned;
        }
        return cleaned;
    }
}