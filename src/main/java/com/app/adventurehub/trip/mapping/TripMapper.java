package com.app.adventurehub.trip.mapping;

import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.trip.domain.model.entity.*;
import com.app.adventurehub.trip.domain.persistence.CategoryRepository;
import com.app.adventurehub.trip.domain.persistence.DestinationRepository;
import com.app.adventurehub.trip.domain.persistence.SeasonRepository;
import com.app.adventurehub.trip.resource.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TripMapper implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    EnhancedModelMapper mapper;

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    DestinationRepository destinationRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    SeasonMapper seasonMapper;

    @Autowired
    TripDetailsMapper tripDetailsMapper;

    @Autowired
    ItineraryMapper itineraryMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    DestinationMapper destinationMapper;

    public TripMapper tripMapper() {
        return new TripMapper(mapper, seasonRepository, categoryRepository,destinationRepository, categoryMapper, seasonMapper, tripDetailsMapper, itineraryMapper, activityMapper, destinationMapper);
    }

    public TripResource toResource(Trip model){
        TripResource resource = new TripResource();
        resource.setId(model.getId());
        resource.setName(model.getName());
        resource.setDescription(model.getDescription());
        resource.setStart_date(model.getStart_date());
        resource.setEnd_date(model.getEnd_date());
        resource.setPrice(model.getPrice());
        resource.setDifficulty(model.getCategory().getName());
        resource.setStatus(model.getStatus());
        resource.setGroup_size(model.getGroup_size());
        resource.setCategory(categoryMapper.toResource(model.getCategory()).getName());
        resource.setSeason(seasonMapper.toResource(model.getSeason()).getName());
        resource.setDestination(destinationMapper.toResource(model.getDestination()));
        resource.setImages(tripDetailsMapper.toResourceList(model.getTripDetails()));
        resource.setItineraries(itineraryMapper.toResourceList(model.getItineraries()));

        return resource;
    }

    public Trip toModel(CreateTripResource resource) {
        Set<TripDetails> tripDetails = new HashSet<>();
        Set<Itinerary> itineraries = new HashSet<>();

        Optional<Season> season = seasonRepository.findById(resource.getSeasonId());
        if (!season.isPresent()) {
            throw new RuntimeException("Season not found");
        }

        Optional<Category> category = categoryRepository.findById(resource.getCategoryId());
        if (!category.isPresent()) {
            throw new RuntimeException("Category not found");
        }

        Optional<Destination> destination = destinationRepository.findById(resource.getDestinationId());
        if (!destination.isPresent()) {
            throw new RuntimeException("Destination not found");
        }

        Trip trip = new Trip();
        trip.setName(resource.getName());
        trip.setDescription(resource.getDescription());
        trip.setPrice(resource.getPrice());
        trip.setStart_date(resource.getStart_date());
        trip.setEnd_date(resource.getEnd_date());
        trip.setSeason(season.get());
        trip.setGroup_size(resource.getGroup_size());
        trip.setStatus(resource.getStatus());
        trip.setCategory(category.get());
        trip.setDestination(destination.get());

        for (String imageUrl : resource.getImages()) {
            TripDetails details = tripDetailsMapper.toModel(imageUrl, trip);
            tripDetails.add(details);
        }
        trip.setTripDetails(tripDetails);

        for (CreateItineraryResource itineraryResource : resource.getItineraries()) {
            Itinerary itinerary = itineraryMapper.toModel(itineraryResource, trip);
            Set<Activity> activities = new HashSet<>();
            for (String activityResource : itineraryResource.getActivities()) {
                Activity activity = activityMapper.toModel(activityResource, itinerary);
                activities.add(activity);
            }
            itinerary.setActivities(activities);
            itineraries.add(itinerary);
        }
        trip.setItineraries(itineraries);

        return trip;
    }

    public List<TripResource> toResourceList(List<Trip> modelList){
        return modelList.stream().map(this::toResource).collect(Collectors.toList());
    }
}
