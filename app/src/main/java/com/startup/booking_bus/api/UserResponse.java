package com.startup.booking_bus.api;

// UserResponse.java
public class UserResponse {
    private boolean success;
    private String message;
    private UserData data;

    public static class UserData {
        private String name;
        private String email;
        private String phone;

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public UserData getData() { return data; }
}