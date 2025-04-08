package com.startup.booking_bus.api;

import com.startup.booking_bus.model.Bus;

import java.util.List;

// BusResponse.java
public class BusResponse {
    private boolean success;
    private PaginatedData data;
    private String message;

    public static class PaginatedData {
        private List<Bus> data;
        private int currentPage;
        private String nextPageUrl;
        private int total;

        public List<Bus> getData() { return data; }
    }

    public boolean isSuccess() { return success; }
    public PaginatedData getData() { return data; }
    public String getMessage() { return message; }
}