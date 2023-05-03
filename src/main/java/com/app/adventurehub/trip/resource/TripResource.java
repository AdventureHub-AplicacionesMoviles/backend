package com.app.adventurehub.trip.resource;

import com.app.adventurehub.trip.domain.model.enumeration.Seasons;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
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
    private String category;
    private Seasons season;
    private DestinationResource destination;
    private Set<String> images = new HashSet<>();
    private Set<ItineraryResource> itineraries = new HashSet<>();


}
