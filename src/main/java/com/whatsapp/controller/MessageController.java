package com.whatsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.whatsapp.entity.Contact;
import com.whatsapp.service.ChatService;
import com.whatsapp.service.ExcelService;
import com.whatsapp.service.WhatsAppService;
import com.whatsapp.service.SessionService;
import com.whatsapp.entity.ChatStep;

@RestController
@RequestMapping("/api")
public class MessageController {
	
	@Autowired
	private ChatService chatService;
    @Autowired
    private ExcelService excelService;

    @Autowired
    private WhatsAppService whatsappService;

    @Autowired
    private SessionService sessionService;   // ✅ IMPORTANT

    @Value("${excel.file.path}")
    private String filePath;

    @GetMapping("/send")
    public String sendMessages() {

        List<Contact> contacts = excelService.readExcel(filePath);

        for (Contact c : contacts) {

            String phone = c.getPhone();

            if (!phone.startsWith("91")) {
                phone = "91" + phone;
            }

            // initialize state
            sessionService.setStep(phone, ChatStep.START);

            // get first chatbot message
            String firstMessage = chatService.processMessage(phone, "", null);

            // send message
            whatsappService.sendMessage(phone, firstMessage);
        }

        return "Messages Sent Successfully!";
    }
}