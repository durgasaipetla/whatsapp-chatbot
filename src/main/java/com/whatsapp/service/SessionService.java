
package com.whatsapp.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import com.whatsapp.entity.Candidate;
import com.whatsapp.entity.ChatStep;

@Service
public class SessionService {

    private Map<String, ChatStep> userState = new ConcurrentHashMap<>();
    private Map<String, Candidate> candidateData = new ConcurrentHashMap<>();

    public ChatStep getStep(String phone) {
        return userState.getOrDefault(phone, ChatStep.START);
    }

    public void setStep(String phone, ChatStep step) {
        userState.put(phone, step);
    }

    public Candidate getCandidate(String phone) {
        return candidateData.computeIfAbsent(phone, k -> new Candidate());
    }
}