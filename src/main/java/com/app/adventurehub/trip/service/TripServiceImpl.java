package com.app.adventurehub.trip.service;

import com.app.adventurehub.shared.exception.ResourceValidationException;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.model.enumeration.Seasons;
import com.app.adventurehub.trip.domain.persistence.TripRepository;
import com.app.adventurehub.trip.domain.service.TripService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TripServiceImpl implements TripService {
    private static final String ENTITY = "Trips";
    private final TripRepository tripRepository;

    @Override
    public List<Trip> GetAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip getTripById(Long tripId) {
        HashMap<String, List<String>> errors = new HashMap<>();
        Optional<Trip> trip = tripRepository.findById(tripId);

        if(!trip.isPresent()) {
            errors.put(ENTITY, List.of("Trip not found"));
        }

        if(!errors.isEmpty()) {
            throw new ResourceValidationException(ENTITY,errors);
        }

        return trip.get();
    }

    @Override
    public List<Trip> getTripByFilter(String destination, Seasons season, Double minPrice, Double maxPrice) {
        return tripRepository.findAllByFilter(destination, season, minPrice, maxPrice);
    }

    @Override
    public Trip create(Trip trip) {
        HashMap<String, List<String>> errors = new HashMap<>();
        Trip tripWithName = tripRepository.findByName(trip.getName());

        if(tripWithName != null) {
            errors.put(ENTITY, List.of("Name already exists"));
        }

        if(!errors.isEmpty()) {
            throw new ResourceValidationException("Trip", errors);
        }

        return tripRepository.save(trip);
    }
}