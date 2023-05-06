package com.app.adventurehub.trip.service;

import com.app.adventurehub.shared.exception.ResourceValidationException;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.persistence.SeasonRepository;
import com.app.adventurehub.trip.domain.persistence.TripRepository;
import com.app.adventurehub.trip.domain.service.TripService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    public List<Trip> getTripByPrice(Double price) {
        return tripRepository.findAllByPrice(price);
    }

    @Override
    public List<Trip> getTripBySeason(String name) {
        return tripRepository.findAllBySeason(name);
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
