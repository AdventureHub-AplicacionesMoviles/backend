package com.app.adventurehub.trip.resource;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateItineraryResource {
    private int day;
    private String location;
    private Coordinates coordinates;
    @NotNull
    @NotEmpty
    @Size(min=1,max=10)
    private Set<CreateActivityResource> activities = new HashSet<>();
}
