package com.whatsapp.entity;


public class Contact {
    private String name;
    private String phone;
    private String message;

    public Contact(String name, String phone, String message) {
        this.name = name;
        this.phone = phone;
        this.message = message;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getMessage() { return message; }
}