package com.app.adventurehub.trip.service;

import com.app.adventurehub.booking.domain.model.entity.Booking;
import com.app.adventurehub.booking.domain.persistence.BookingRepository;
import com.app.adventurehub.shared.exception.ResourceValidationException;
import com.app.adventurehub.trip.TripStatus;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.persistence.TripRepository;
import com.app.adventurehub.trip.domain.service.TripService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TripServiceImpl implements TripService {
	private static final String ENTITY = "Trips";
	private final BookingRepository bookingRepository;
	private final TripRepository tripRepository;

	private void validateAndHandleStock(Trip trip) {
		if (trip.getStock() <= 0) {
			throwValidationException("No stock available");
		}
		if (trip.getStock() == 1) {
			trip.setStatus(TripStatus.INACTIVE);
		}
	}

	private void updateTripStockAndStatus(Trip trip) {
		Long newStock = trip.getStock() - 1;
		trip.setStock(newStock);
	}

	private void throwValidationException(String errorMessage) {
		HashMap<String, List<String>> errors = new HashMap<>();
		throw new ResourceValidationException(errorMessage, errors);
	}

	@Override
	public List<Trip> getAll() {
		return tripRepository.findAll();
	}

	@Override
	public List<Trip> getFilteredTrips(String destination, String season, Double minPrice, Double maxPrice) {
		List<Trip> trips = new ArrayList<>();
		for (Trip trip : getAll()) {
			if (destination == null || trip.getDestination().getName().contains(destination)) {
				if (season == null || trip.getSeason().getName().equals(season)) {
					if (minPrice == null || trip.getPrice() >= minPrice) {
						if (maxPrice == null || trip.getPrice() <= maxPrice) {
							trips.add(trip);
						}
					}
				}
			}
		}
		return trips;
		// return tripRepository.findByDestinationContainingAndSeasonAndPriceBetween(
		// destination, season, minPrice, maxPrice);
	}

	@Override
	public Trip getTripById(Long tripId) {
		HashMap<String, List<String>> errors = new HashMap<>();
		Optional<Trip> trip = tripRepository.findById(tripId);

		if (!trip.isPresent()) {
			errors.put(ENTITY, List.of("Trip not found"));
		}

		if (!errors.isEmpty()) {
			throw new ResourceValidationException(ENTITY, errors);
		}

		return trip.get();
	}

	@Override
	public Trip create(Trip trip) {
		HashMap<String, List<String>> errors = new HashMap<>();
		Trip tripWithName = tripRepository.findByName(trip.getName());

		if (tripWithName != null) {
			errors.put(ENTITY, List.of("Name already exists"));
		}

		if (!errors.isEmpty()) {
			throw new ResourceValidationException("Trip", errors);
		}

		return tripRepository.save(trip);
	}

	@Override
	public List<Trip> createTrips(List<Trip> trips) {
		return tripRepository.saveAll(trips);
	}

	@Override
	public Trip bookTrip(Long tripId) {
		Trip trip = getTripById(tripId);
		validateAndHandleStock(trip);
		updateTripStockAndStatus(trip);
		return tripRepository.save(trip);
	}

	// Agencia
	// 1. Obtener todos los paquetes que ha creado
	// 2. Paquete tiene un stock
	// 3. Por los que han sido reservados -> bookings.forEach(booking ->
	// booking.trip.user.id == agencyId)
	@Override
	public List<Trip> getTripsByAgency(Long agencyId) {
		return tripRepository.findAllByAgencyId(agencyId);
	}

	@Override
	public List<Trip> getTripsByTraveler(Long travelerId) {

		// Step 1: Get all the trips that the traveler has booked
		List<Booking> bookings = bookingRepository.findAllByTravelerId(travelerId);

		// Step 2: Get all the trips from every booking trip.id
		List<Trip> trips = tripRepository
				.findAllById(bookings.stream().map(booking -> booking.getTrip().getId()).collect(Collectors.toList()));

		return trips;
	}
}