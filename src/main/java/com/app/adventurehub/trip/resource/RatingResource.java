package com.app.adventurehub.trip.resource;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class RatingResource {
    private Long id;
    private String comment;
    private int rating;
}
