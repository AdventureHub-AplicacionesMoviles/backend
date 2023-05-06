package com.app.adventurehub.trip.domain.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;
    private String description;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.PERSIST)
    private List<Trip> trips = new ArrayList<>();

    public Destination(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
