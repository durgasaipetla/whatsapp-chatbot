package com.whatsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whatsapp.entity.Candidate;
import com.whatsapp.entity.ChatStep;

@Service
public class ChatService {

    @Autowired
    private SessionService sessionService;

    public String processMessage(String phone, String message, String mediaUrl) {

        ChatStep step = sessionService.getStep(phone);
        Candidate candidate = sessionService.getCandidate(phone);

        switch (step) {

            case START:

                sessionService.setStep(phone, ChatStep.ASK_AVAILABILITY);

                return "Hello! We are reaching out from Tanish Solutions regarding a job opportunity.\n"
                        + "Are you available right now? (Yes/No)";

            case ASK_AVAILABILITY:

                if (message.equalsIgnoreCase("no")) {

                    sessionService.setStep(phone, ChatStep.COMPLETED);

                    return "No problem. We will connect with you later. Thank you!";
                }

                if (message.equalsIgnoreCase("yes")) {

                    sessionService.setStep(phone, ChatStep.ASK_TOTAL_EXP);

                    return "Great! What is your total years of experience?";
                }

                return "Please reply with Yes or No.";

            case ASK_TOTAL_EXP:

                candidate.setTotalExp(message);

                sessionService.setStep(phone, ChatStep.ASK_RELEVANT_EXP);

                return "What is your relevant experience for this role?";

            case ASK_RELEVANT_EXP:

                candidate.setRelevantExp(message);

                sessionService.setStep(phone, ChatStep.ASK_CURRENT_CTC);

                return "What is your current CTC?";

            case ASK_CURRENT_CTC:

                candidate.setCurrentCtc(message);

                sessionService.setStep(phone, ChatStep.ASK_EXPECTED_CTC);

                return "What is your expected CTC?";

            case ASK_EXPECTED_CTC:

                candidate.setExpectedCtc(message);

                sessionService.setStep(phone, ChatStep.ASK_NOTICE_PERIOD);

                return "What is your notice period?";

            case ASK_NOTICE_PERIOD:

                candidate.setNoticePeriod(message);

                sessionService.setStep(phone, ChatStep.ASK_RESUME);

                return "Please share your updated resume.";

            case ASK_RESUME:

                if (mediaUrl != null) {

                    candidate.setResumeUrl(mediaUrl);

                    sessionService.setStep(phone, ChatStep.COMPLETED);

                    return "Thank you! Our recruitment team will review your profile and get back to you.";
                }

                return "Kindly upload your resume to proceed.";

            default:

                return "Conversation completed. Thank you!";
        }
    }
}