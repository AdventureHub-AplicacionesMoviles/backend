package com.app.adventurehub.trip.resource;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateRatingResource {
    Long userId;
    Long tripId;
    String comment;
    int rating;
}
