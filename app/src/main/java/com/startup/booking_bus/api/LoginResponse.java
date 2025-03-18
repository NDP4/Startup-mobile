package com.startup.booking_bus.api;

public class LoginResponse {
    private boolean success;
    private String message;
    private UserData data;

    public static class UserData {
        private String user;
        private String email;
        private String token;

        // Getters
        public String getToken() { return token; }
        public String getUser() { return user; }
        public String getEmail() { return email; }
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public UserData getData() { return data; }
}