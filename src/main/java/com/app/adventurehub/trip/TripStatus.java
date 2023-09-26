package com.app.adventurehub.trip;

public enum TripStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    TripStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
