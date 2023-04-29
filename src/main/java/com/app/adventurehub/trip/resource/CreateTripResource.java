package com.app.adventurehub.trip.resource;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateTripResource {
    private String status = "A";
    private String name;
    private String description;
    @Column(precision = 10, scale = 2)
    private Double price;
    private Date start_date;
    private Date end_date;
    private String group_size;
    private Long seasonId;
    private Long categoryId;
    private Long destinationId;
    private Set<CreateTripDetailsResource> tripDetails;
    private Set<CreateItineraryResource> itineraries;
}