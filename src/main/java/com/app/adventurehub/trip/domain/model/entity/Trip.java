package com.app.adventurehub.trip.domain.model.entity;

import com.app.adventurehub.trip.TripStatus;
import com.app.adventurehub.user.domain.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TripStatus status = TripStatus.INACTIVE;
    private String name;
    private String description;
    private Date start_date;
    private Date end_date;
    private String group_size;
    private Long stock = 0L;
    @Column(precision = 10, scale = 2)
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "season_id", nullable = false)
    @JsonIgnore
    private Season season;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "destination_id", nullable = false)
    @JsonIgnore
    private Destination destination;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private Set<TripDetails> tripDetails = new HashSet<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private Set<Itinerary> itineraries = new HashSet<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();
}
