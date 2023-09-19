package com.app.adventurehub.trip.domain.service;


import com.app.adventurehub.trip.domain.model.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getByTripId(Long tripId);

    Review create(Review review);

}
