package com.app.adventurehub.trip.domain.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "itinerary")
public class Itinerary {

    public Itinerary(int day, String location, Double latitude, Double longitude, Trip trip) {
        this.day = day;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.trip = trip;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int day;
    private String location;
    private Double latitude;
    private Double longitude;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL)
    private Set<Activity> activities = new HashSet<>();
}
