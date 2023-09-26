package com.app.adventurehub.booking.enums;

public enum BookingStatus {
    CONFIRMED("CONFIRMED"),
    PENDING("PENDING"),
    CANCELED("CANCELED");

    private final String status;

    BookingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
