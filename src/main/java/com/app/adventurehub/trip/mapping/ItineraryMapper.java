package com.app.adventurehub.trip.mapping;

import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.trip.domain.model.entity.Itinerary;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.resource.CreateItineraryResource;
import com.app.adventurehub.trip.resource.ItineraryResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ItineraryMapper implements Serializable{

    private static final long serialVersionUID = 1L;
    @Autowired
    private EnhancedModelMapper modelMapper;

    @Autowired
    private ActivityMapper activityMapper;

    public ItineraryMapper itineraryMapper() {
        return new ItineraryMapper(modelMapper, activityMapper);
    }

    public Itinerary toModel(CreateItineraryResource resource, Trip trip) {
        Itinerary model = new Itinerary(
                resource.getDay(),
                resource.getLocation(),
                resource.getCoordinates().getLatitude(),
                resource.getCoordinates().getLongitude(),
                trip
        );

        return model;
    }

    public ItineraryResource toResource(Itinerary model){
        ItineraryResource resource = new ItineraryResource();
        resource.setId(model.getId());
        resource.setDay(model.getDay());
        resource.setLocation(model.getLocation());
        resource.setLatitude(model.getLatitude());
        resource.setLongitude(model.getLongitude());
        resource.setActivities( activityMapper.toResourceList(model.getActivities()));
        return resource;
    }

    public Set<ItineraryResource> toResourceList(Set<Itinerary> modelList){
        return modelList.stream().map(this::toResource).collect(Collectors.toSet());
    }
}
