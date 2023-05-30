package com.app.adventurehub.trip.domain.service;

import com.app.adventurehub.trip.domain.model.entity.Season;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.model.enumeration.Seasons;

import java.util.List;

public interface TripService {
    List<Trip> GetAll();
    List<Trip> getTripByPrice(Double price);
    Trip create(Trip trip);
    List<Trip> getTripBySeason(String name);
    List<Trip> getTripByDestination(String name);
    List<Trip> getTripByFilter(String destination, Seasons season, Double minPrice, Double maxPrice);
}
