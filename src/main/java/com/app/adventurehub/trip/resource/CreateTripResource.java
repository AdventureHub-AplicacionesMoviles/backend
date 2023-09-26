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
    @NotBlank(message = "Start date is required")
    private Date start_date;

    @NotBlank(message = "End date is required")
    @FutureOrPresent(message = "End date must be present or future")
    private Date end_date;

    @NotBlank(message = "Name is required")
    @Size(max= 50, message = "Name must be less than 50 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max= 255, message = "Description must be less than 255 characters")
    private String description;

    @Positive(message = "Price must be positive")
    @DecimalMin(value = "50.00")
    @DecimalMax(value = "999999.99")
    private Double price;

    @Positive(message = "Group size must be positive")
    @NotBlank(message = "Group size is required")
    private String group_size;

    @Positive(message = "Stock must be positive")
    @NotNull(message = "Stock is required")
    private Long stock;

    @NotNull(message = "Season is required")
    private Long seasonId;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Destination is required")
    private Long destinationId;

    @NotNull(message = "User is required")
    private Long userId;

    @NotBlank
    @NotEmpty
    @Size(min=1,max=5)
    private Set<String> images = new HashSet<>();
    private Set<CreateItineraryResource> itineraries = new HashSet<>();
}