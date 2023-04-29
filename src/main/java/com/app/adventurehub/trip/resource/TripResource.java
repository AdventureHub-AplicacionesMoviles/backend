package com.app.adventurehub.trip.resource;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class TripResource {
    private Long id;
    private String name;
    private String description;
    private Date start_date;
    private Date end_date;
    private Double price;
    private String difficulty;
    private String status;
    private String group_size;
    private CategoryResource category;
    private SeasonResource season;
    private DestinationResource destination;
    private Set<TripDetailsResource> tripDetails;
    private Set<ItineraryResource> itineraries;


}
