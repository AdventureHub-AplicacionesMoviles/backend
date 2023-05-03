package com.app.adventurehub.trip.domain.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="itinerary_id", nullable = false)
    private Itinerary itinerary;

    public Activity(String name, Itinerary itinerary) {
        this.name = name;
        this.itinerary = itinerary;
    }
}
