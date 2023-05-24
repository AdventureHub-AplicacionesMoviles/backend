package com.app.adventurehub.trip.domain.service;


import com.app.adventurehub.trip.domain.model.entity.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> getByTripId(Long tripId);

    Rating create(Rating rating);

}
