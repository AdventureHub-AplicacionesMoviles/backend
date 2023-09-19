package com.app.adventurehub.trip.mapping;

import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.trip.domain.model.entity.Review;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.persistence.TripRepository;
import com.app.adventurehub.trip.resource.CreateReviewResource;
import com.app.adventurehub.trip.resource.ReviewResource;
import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import com.app.adventurehub.user.mapping.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@AllArgsConstructor
public class ReviewMapper implements Serializable {
	
    @Autowired
    EnhancedModelMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    UserMapper userMapper;

    public List<ReviewResource> toResourceList(List<Review> modelList) {
        List<ReviewResource> resourceList = new java.util.ArrayList<>(modelList.size());
        modelList.forEach(model -> resourceList.add(toResource(model)));
        return resourceList;
    }

    public ReviewResource toResource(Review model) {
        ReviewResource resource = new ReviewResource();
        resource.setId(model.getId());
        resource.setComment(model.getComment());
        resource.setRating(model.getRating());
        resource.setUser(userMapper.toResource(model.getUser()));
        resource.setCreatedAt(model.getCreatedAt());
        resource.setUpdatedAt(model.getUpdatedAt());

        return resource;
    }

    public Review toModel(CreateReviewResource resource) {
        Review model = new Review();
        User user = userRepository.findById(resource.getUserId()).get();
        Trip tripResource = tripRepository.findById(resource.getTripId()).get();

        model.setUser(user);
        model.setTrip(tripResource);
        model.setComment(resource.getComment());
        model.setRating(resource.getRating());
        return model;
    }
}
