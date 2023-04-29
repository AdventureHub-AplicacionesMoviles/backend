package com.app.adventurehub.trip.resource;

import java.util.Set;

public class ItineraryResource {
    private Long id;
    private String day;
    private String location;
    private String latitude;
    private String longitude;
    private Set<ActivityResource> activities;
}
