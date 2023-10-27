package com.app.adventurehub.trip.domain.service;

import com.app.adventurehub.trip.domain.model.entity.Trip;

import java.util.List;

public interface TripService {
	List<Trip> getAll();

	List<Trip> getFilteredTrips(String destination, String season, Double minPrice, Double maxPrice);

	Trip getTripById(Long tripId);

	List<Trip> getTripsByAgency(Long agencyId);

	List<Trip> getTripsByTraveler(Long travelerId);

	Trip bookTrip(Long tripId);

	Trip create(Trip trip);

	List<Trip> createTrips(List<Trip> trips);
}
