package com.app.adventurehub.trip.resource;

import com.app.adventurehub.trip.domain.model.enumeration.Seasons;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class SeasonResource {
    private Long id;
    private Seasons name;
}
