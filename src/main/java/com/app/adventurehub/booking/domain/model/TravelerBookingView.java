package com.app.adventurehub.booking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.app.adventurehub.booking.resource.TravelerBookingAggregate;

@Getter
@Setter
@AllArgsConstructor
public class TravelerBookingView {
	List<TravelerBookingAggregate> upcomingBookings;
	List<TravelerBookingAggregate> pastBookings;
}