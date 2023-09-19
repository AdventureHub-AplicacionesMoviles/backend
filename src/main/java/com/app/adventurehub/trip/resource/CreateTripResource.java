package com.app.adventurehub.trip.resource;

import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateTripResource {
    @NotNull
    private Date start_date;

    @NotNull
    @FutureOrPresent
    private Date end_date;

    private String status = "A";

    @NotNull
    @NotBlank
    @Size(max= 50)
    private String name;

    @NotNull
    @NotBlank
    @Size(max= 255)
    private String description;

    @Positive
    @DecimalMin(value = "50.00")
    @DecimalMax(value = "999999.99")
    private Double price;

    @NotNull
    @NotBlank
    private String group_size;

    @NotNull
    private Long seasonId;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long destinationId;

    @NotNull
    private Long userId;

    @NotNull
    @NotEmpty
    @Size(min=1,max=5)
    private Set<String> images = new HashSet<>();
    private Set<CreateItineraryResource> itineraries = new HashSet<>();
}