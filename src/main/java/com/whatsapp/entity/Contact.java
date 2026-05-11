package com.whatsapp.entity;

public class Contact {
    private String name;
    private String phone;
    private boolean consent;

    public Contact() {}

    public Contact(String name, String phone, boolean consent) {
        this.name = name;
        this.phone = phone;
        this.consent = consent;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public boolean isConsent() { return consent; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setConsent(boolean consent) { this.consent = consent; }
}