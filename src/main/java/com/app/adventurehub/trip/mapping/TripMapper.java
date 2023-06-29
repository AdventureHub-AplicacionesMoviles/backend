package com.app.adventurehub.trip.mapping;

import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.trip.domain.model.entity.*;
import com.app.adventurehub.trip.domain.persistence.CategoryRepository;
import com.app.adventurehub.trip.domain.persistence.DestinationRepository;
import com.app.adventurehub.trip.domain.persistence.ReviewRepository;
import com.app.adventurehub.trip.domain.persistence.SeasonRepository;
import com.app.adventurehub.trip.resource.*;
import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import com.app.adventurehub.user.mapping.UserMapper;
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
    UserRepository userRepository;
    @Autowired
    SeasonRepository seasonRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DestinationRepository destinationRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    SeasonMapper seasonMapper;
    @Autowired
    TripDetailsMapper tripDetailsMapper;
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    ItineraryMapper itineraryMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    DestinationMapper destinationMapper;

    @Autowired
    UserMapper userMapper;

    public TripMapper tripMapper() {
        return new TripMapper(
                mapper,
                userRepository,
                seasonRepository,
                categoryRepository,
                destinationRepository,
                reviewRepository,
                categoryMapper,
                seasonMapper,
                tripDetailsMapper,
                reviewMapper,
                itineraryMapper,
                activityMapper,
                destinationMapper,
                userMapper);
    }

    public TripAggregateResource toAggregateResource(Trip model) {
        TripAggregateResource resource = new TripAggregateResource();

        return resource
                .withId( model.getId() )
                .withName( model.getName() )
                .withDescription( model.getDescription() )
                .withPrice( model.getPrice() )
                .withStatus( model.getStatus() )
                .withStart_date( model.getStart_date())
                .withEnd_date( model.getEnd_date() )
                .withGroup_size( model.getGroup_size() )
                .withCategory( model.getCategory().getName() )
                .withAgency_name( model.getUser().getUsername() )
                .withSeason( seasonMapper.toResource(model.getSeason()) )
                .withDestination( destinationMapper.toResource(model.getDestination()) )
                .withImages( model.getTripDetails()
                                    .stream()
                                    .map(TripDetails::getImageUrl)
                                    .collect(Collectors.toSet()) )
                .withItineraries( model.getItineraries()
                                    .stream()
                                    .map(it -> itineraryMapper.toResource(it)).collect(Collectors.toSet()) )
                .withReviews( model.getReviews()
                                    .stream()
                                    .map( rv -> reviewMapper.toResource(rv)).collect(Collectors.toSet()) );
    }

    public TripResource toResource(Trip model){
        TripResource resource = new TripResource();
        resource.setId(model.getId());
        resource.setName(model.getName());
        resource.setPrice(model.getPrice());
        resource.setDestination_name(model.getDestination().getName());
        resource.setStart_date(model.getStart_date());
        resource.setEnd_date(model.getEnd_date());
        resource.setStatus(model.getStatus());

        Optional<TripDetails> thumbnail = model.getTripDetails().stream().findFirst();
        resource.setThumbnail( thumbnail.isPresent() ? thumbnail.get().getImageUrl() : "" );

        double averageRating = reviewRepository
                .findAllByTripId(model.getId())
                .stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        double normalizedRating = Math.min(Math.max(averageRating, 0.0), 5.0);
        resource.setAverage_rating(String.valueOf(normalizedRating));

        return resource;
    }

    public Trip toModel(CreateTripResource resource) {
        Trip trip = createTripFromResource(resource);
        Set<TripDetails> tripDetails = createTripDetailsSet(resource.getImages(), trip);
        Set<Itinerary> itineraries = createItinerariesSet(resource.getItineraries(), trip);

        trip.setTripDetails(tripDetails);
        trip.setItineraries(itineraries);

        return trip;
    }

		public List<Trip> toModelList(List<CreateTripResource> resources) {
				return resources.stream().map(this::toModel).collect(Collectors.toList());
		}

    private Trip createTripFromResource(CreateTripResource resource) {
        Season season = seasonRepository.findById(resource.getSeasonId())
                .orElseThrow(() -> new RuntimeException("Season not found"));
        Category category = categoryRepository.findById(resource.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Destination destination = destinationRepository.findById(resource.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        User user = userRepository.findById(resource.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Trip trip = new Trip();
        trip.setName(resource.getName());
        trip.setDescription(resource.getDescription());
        trip.setPrice(resource.getPrice());
        trip.setStart_date(resource.getStart_date());
        trip.setEnd_date(resource.getEnd_date());
        trip.setSeason(season);
        trip.setGroup_size(resource.getGroup_size());
        trip.setStatus(resource.getStatus());
        trip.setCategory(category);
        trip.setDestination(destination);
        trip.setUser(user);

        return trip;
    }

    private Set<TripDetails> createTripDetailsSet(Set<String> imageUrls, Trip trip) {
        Set<TripDetails> tripDetails = new HashSet<>();
        for (String imageUrl : imageUrls) {
            TripDetails details = tripDetailsMapper.toModel(imageUrl, trip);
            tripDetails.add(details);
        }
        return tripDetails;
    }

    private Set<Itinerary> createItinerariesSet(Set<CreateItineraryResource> itineraryResources, Trip trip) {
        Set<Itinerary> itineraries = new HashSet<>();
        for (CreateItineraryResource itineraryResource : itineraryResources) {
            Itinerary itinerary = itineraryMapper.toModel(itineraryResource, trip);
            Set<Activity> activities = createActivitiesSet(itineraryResource.getActivities(), itinerary);
            itinerary.setActivities(activities);
            itineraries.add(itinerary);
        }
        return itineraries;
    }

    private Set<Activity> createActivitiesSet(Set<CreateActivityResource> activityResources, Itinerary itinerary) {
        Set<Activity> activities = new HashSet<>();
        for (CreateActivityResource activityResource : activityResources) {
            Activity activity = activityMapper.toModel(activityResource,itinerary);
            activities.add(activity);
        }
        return activities;
    }

    public List<TripResource> toResourceList(List<Trip> modelList){
        return modelList.stream().map(this::toResource).collect(Collectors.toList());
    }
}
