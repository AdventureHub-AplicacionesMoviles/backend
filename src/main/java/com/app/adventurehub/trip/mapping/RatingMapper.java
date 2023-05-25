package com.app.adventurehub.trip.mapping;

import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.trip.domain.model.entity.Rating;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.persistence.TripRepository;
import com.app.adventurehub.trip.resource.CreateRatingResource;
import com.app.adventurehub.trip.resource.RatingResource;
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
public class RatingMapper implements Serializable {
	
    @Autowired
    EnhancedModelMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    UserMapper userMapper;

    public List<RatingResource> toResourceList(List<Rating> modelList) {
        List<RatingResource> resourceList = new java.util.ArrayList<>(modelList.size());
        modelList.forEach(model -> resourceList.add(toResource(model)));
        return resourceList;
    }

    public RatingResource toResource(Rating model) {
        RatingResource resource = new RatingResource();
        resource.setId(model.getId());
        resource.setComment(model.getComment());
        resource.setRating(model.getRating());
        resource.setUser(userMapper.toResource(model.getUser()));
        return resource;
    }

    public Rating toModel(CreateRatingResource resource) {
        Rating model = new Rating();
        User user = userRepository.findById(resource.getUserId()).get();
        Trip tripResource = tripRepository.findById(resource.getTripId()).get();

        model.setUser(user);
        model.setTrip(tripResource);
        model.setComment(resource.getComment());
        model.setRating(resource.getRating());
        return model;
    }
}
