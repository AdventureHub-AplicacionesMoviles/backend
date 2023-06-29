package com.app.adventurehub.trip.domain.service;

import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.model.enumeration.Seasons;

import java.util.List;

public interface TripService {
    List<Trip> GetAll();
    Trip getTripById(Long tripId);
    Trip create(Trip trip);
		List<Trip> createTrips(List<Trip> trips);
    List<Trip> getTripByFilter(String destination, Seasons season, Double minPrice, Double maxPrice);
}
