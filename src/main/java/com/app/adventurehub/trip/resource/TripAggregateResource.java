package com.app.adventurehub.trip.resource;

import com.app.adventurehub.trip.TripStatus;
import com.app.adventurehub.trip.domain.model.entity.Destination;
import com.app.adventurehub.trip.domain.model.entity.Season;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class TripAggregateResource {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private TripStatus status;
    private Date start_date;
    private Date end_date;
    private String group_size;
    private String category;
    private String agency_name;
    private SeasonResource season;
    private DestinationResource destination;
    private Set<String> images = new HashSet<>();
    private Set<ItineraryResource> itineraries = new HashSet<>();
    private Set<ReviewResource> reviews = new HashSet<>();
}
