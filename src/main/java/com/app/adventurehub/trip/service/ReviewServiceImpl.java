package com.app.adventurehub.trip.service;

import com.app.adventurehub.trip.domain.model.entity.Review;
import com.app.adventurehub.trip.domain.persistence.ReviewRepository;
import com.app.adventurehub.trip.domain.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private final ReviewRepository reviewRepository;
    private static final String ENTITY = "Reviews";
    @Override
    public List<Review> getByTripId(Long tripId) {
        return reviewRepository.findAllByTripId(tripId);
    }

    @Override
    public Review create(Review review) {
        return reviewRepository.save(review);
    }
}
