package com.app.adventurehub.trip.resource;

import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateActivityResource {
    private String name;
    private String description;
}
