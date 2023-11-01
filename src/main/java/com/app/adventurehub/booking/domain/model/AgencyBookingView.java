package com.app.adventurehub.booking.domain.model;

import java.util.List;

import com.app.adventurehub.booking.domain.model.entity.Booking;
import com.app.adventurehub.trip.domain.model.entity.Trip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AgencyBookingView {
	Trip trip;
	List<Booking> bookings;
}