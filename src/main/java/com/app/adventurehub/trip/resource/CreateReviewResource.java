package com.app.adventurehub.trip.resource;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewResource {
    Long userId;
    Long tripId;
    String comment;
    int rating;
}
