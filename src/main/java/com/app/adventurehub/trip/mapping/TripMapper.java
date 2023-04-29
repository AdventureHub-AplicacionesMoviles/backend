package com.app.adventurehub.trip.mapping;

import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.trip.domain.model.entity.*;
import com.app.adventurehub.trip.domain.persistence.CategoryRepository;
import com.app.adventurehub.trip.domain.persistence.DestinationRepository;
import com.app.adventurehub.trip.domain.persistence.SeasonRepository;
import com.app.adventurehub.trip.resource.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class TripMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    DestinationRepository destinationRepository;

    @Autowired
    CategoryRepository categoryRepository;

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
        resource.setCategory(mapper.map(model.getCategory(), CategoryResource.class));
        resource.setSeason(mapper.map(model.getSeason(), SeasonResource.class));
        resource.setDestination(mapper.map(model.getDestination(), DestinationResource.class));
        resource.setTripDetails(Collections.singleton(mapper.map(model.getTripDetails(), TripDetailsResource.class)));
        resource.setItineraries(Collections.singleton(mapper.map(model.getItineraries(), ItineraryResource.class)));

        return resource;
    }

    public Trip toModel(CreateTripResource resource) {
        Optional<Season> season = seasonRepository.findById(resource.getSeasonId());
        if (!season.isPresent()) {
            throw new RuntimeException("Season not found");
        }

        Optional<Destination> destination = destinationRepository.findById(resource.getDestinationId());
        if (!destination.isPresent()) {
            throw new RuntimeException("Destination not found");
        }

        Optional<Category> category = categoryRepository.findById(resource.getCategoryId());
        if (!category.isPresent()) {
            throw new RuntimeException("Category not found");
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
        trip.setDestination(destination.get());
        trip.setCategory(category.get());

        Set<TripDetails> tripDetails = new HashSet<>();
        for (CreateTripDetailsResource detailsResource : resource.getTripDetails()) {
            TripDetails tripDetailsModel = mapper.map(detailsResource, TripDetails.class);
            tripDetailsModel.setTrip(trip);
            tripDetails.add(tripDetailsModel);
        }
        trip.setTripDetails(tripDetails);

        Set<Itinerary> itineraries = new HashSet<>();
        for (CreateItineraryResource itineraryResource : resource.getItineraries()) {
            Itinerary itineraryModel = mapper.map(itineraryResource, Itinerary.class);
            itineraryModel.setTrip(trip);
            itineraries.add(itineraryModel);
        }
        trip.setItineraries(itineraries);

        return trip;
    }

    public List<TripResource> toResourceList(List<Trip> modelList){
        return modelList.stream().map(this::toResource).collect(Collectors.toList());
    }
}
