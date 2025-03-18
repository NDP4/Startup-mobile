package com.startup.booking_bus.api;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;

    public RegisterRequest(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}