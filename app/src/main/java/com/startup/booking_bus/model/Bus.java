package com.startup.booking_bus.model;

import com.google.gson.annotations.SerializedName;

public class Bus {
    private static final String BASE_IMAGE_URL = "https://bus.ndp.my.id/storage/";

    private int id;

    @SerializedName("images")
    private String[] images;

    private String name;

    @SerializedName("number_plate")
    private String numberPlate;

    private String description;

    @SerializedName("default_seat_capacity")
    private int defaultSeatCapacity;

    private String status;

    @SerializedName("pricing_type")
    private String pricingType;

    @SerializedName("price_per_day")
    private double pricePerDay;

    @SerializedName("price_per_km")
    private double pricePerKm;

    // Getters
    public int getId() { return id; }
    public String getImage() {
        if (images != null && images.length > 0) {
            return BASE_IMAGE_URL + images[0];
        }
        return "";
    }
    public String getName() { return name; }
    public String getNumberPlate() { return numberPlate; }
    public String getDescription() { return description; }
    public int getDefaultSeatCapacity() { return defaultSeatCapacity; }
    public String getStatus() { return status; }
    public String getPricingType() { return pricingType; }
    public double getPricePerDay() { return pricePerDay; }
    public double getPricePerKm() { return pricePerKm; }
}