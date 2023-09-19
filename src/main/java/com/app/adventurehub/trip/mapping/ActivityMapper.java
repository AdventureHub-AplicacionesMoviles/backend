package com.app.adventurehub.trip.mapping;

import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.trip.domain.model.entity.Activity;
import com.app.adventurehub.trip.domain.model.entity.Itinerary;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.resource.ActivityResource;
import com.app.adventurehub.trip.resource.CreateActivityResource;
import com.app.adventurehub.trip.resource.CreateItineraryResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ActivityMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EnhancedModelMapper modelMapper;

    public ActivityMapper activityMapper() {
        return new ActivityMapper(modelMapper);
    }

    public Activity toModel(CreateActivityResource resource,Itinerary itinerary) {
        Activity model = new Activity();
        return model
                .withName(resource.getName())
                .withDescription(resource.getDescription())
                .withItinerary(itinerary);
    }

    public ActivityResource toResource(Activity model) {
        ActivityResource resource = new ActivityResource();
        return resource
                .withId(model.getId())
                .withName(model.getName())
                .withDescription(model.getDescription());
    }

    public Set<ActivityResource> toResourceList(Set<Activity> modelList){
        return modelList.stream().map(this::toResource).collect(Collectors.toSet());
    }
}
