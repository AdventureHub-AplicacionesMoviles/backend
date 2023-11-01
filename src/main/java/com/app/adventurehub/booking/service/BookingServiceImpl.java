package com.app.adventurehub.booking.service;

import com.app.adventurehub.booking.domain.model.entity.Booking;
import com.app.adventurehub.booking.domain.persistence.BookingRepository;
import com.app.adventurehub.booking.domain.service.BookingService;
import com.app.adventurehub.booking.resource.TravelerBookingAggregate;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.service.TripService;
import com.app.adventurehub.trip.mapping.TripMapper;
import com.app.adventurehub.booking.domain.model.AgencyBookingView;
import com.app.adventurehub.booking.domain.model.TravelerBookingView;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
	private final TripMapper mapper;
	private final TripService tripService;
	private final BookingRepository bookingRepository;

	public List<AgencyBookingView> groupBookingsByTrip(List<Booking> bookings) {
		return bookings.stream()
				.collect(Collectors.groupingBy(Booking::getTrip))
				.entrySet().stream()
				.map(entry -> {
					Trip trip = entry.getKey();
					trip.setUser(null);
					trip.getCategory().setTrips(null);
					trip.getItineraries().forEach(itinerary -> {
						itinerary.setTrip(null);
						itinerary.setActivities(null);
					});

					List<Booking> bookingsWithoutTrip = entry.getValue().stream()
							.map(booking -> {
								booking.setTrip(null);
								booking.setUser(null);
								return booking;
							})
							.collect(Collectors.toList());
					return new AgencyBookingView(entry.getKey(), bookingsWithoutTrip);
				})
				.collect(Collectors.toList());
	}

	public TravelerBookingAggregate getTravelerBookingAggregate(Booking booking) {
		return new TravelerBookingAggregate(
				booking.getId(),
				booking.getDate(),
				booking.getStatus(),
				booking.getNumberOfPeople(),
				mapper.toAggregateResource(booking.getTrip()));
	}

	@Override
	public List<Booking> getAll() {
		return bookingRepository.findAll();
	}

	@Override
	public TravelerBookingView getTravelerBookings(Long userId) {
		Date currentDate = new Date();
		List<Booking> upcomingBookings = bookingRepository.findAllUpcomingBookings(userId, currentDate);
		List<Booking> pastBookings = bookingRepository.findAllPastBookings(userId, currentDate);

		List<TravelerBookingAggregate> upcomingBookingsAggregate = upcomingBookings.stream()
				.map(this::getTravelerBookingAggregate)
				.collect(Collectors.toList());

		List<TravelerBookingAggregate> pastBookingsAggregate = pastBookings.stream()
				.map(this::getTravelerBookingAggregate)
				.collect(Collectors.toList());

		return new TravelerBookingView(upcomingBookingsAggregate, pastBookingsAggregate);
	}

	@Override
	public List<AgencyBookingView> getAgencyBookings(Long userId) {
		List<Booking> bookings = bookingRepository.findAllByAgencyId(userId);

		return groupBookingsByTrip(bookings);
	}

	@Override
	public Booking create(Booking booking) {
		tripService.bookTrip(booking.getTrip().getId());
		return bookingRepository.save(booking);
	}
}
