package com.app.adventurehub.booking.domain.service;

import com.app.adventurehub.booking.domain.model.entity.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> getAll();
    List<Booking> getAllByUserId(Long id);
    Booking create(Booking booking);

}
