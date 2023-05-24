package com.app.adventurehub.trip.service;

import com.app.adventurehub.trip.domain.model.entity.Rating;
import com.app.adventurehub.trip.domain.persistence.RatingRepository;
import com.app.adventurehub.trip.domain.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
    @Autowired
    private final RatingRepository ratingRepository;
    private static final String ENTITY = "Ratings";
    @Override
    public List<Rating> getByTripId(Long tripId) {
        return ratingRepository.findByTripId(tripId);
    }

    @Override
    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }
}
