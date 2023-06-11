package com.app.adventurehub.trip.resource;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryResource {
    private Long id;
    private int day;
    private String location;
    private Double latitude;
    private Double longitude;
    private Set<ActivityResource> activities = new HashSet<>();
}
