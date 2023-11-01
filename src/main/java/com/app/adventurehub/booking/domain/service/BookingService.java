package com.app.adventurehub.booking.domain.service;

import com.app.adventurehub.booking.domain.model.AgencyBookingView;
import com.app.adventurehub.booking.domain.model.TravelerBookingView;
import com.app.adventurehub.booking.domain.model.entity.Booking;

import java.util.List;

public interface BookingService {
	List<Booking> getAll();

	TravelerBookingView getTravelerBookings(Long id);

	List<AgencyBookingView> getAgencyBookings(Long id);

	Booking create(Booking booking);

}
