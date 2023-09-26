package com.app.adventurehub.trip.resource;

import com.app.adventurehub.trip.TripStatus;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class TripResource {
    private Long id;
    private TripStatus status;
    private String name;
    private Date start_date;
    private Date end_date;
    private Double price;
    private String destination_name;
    private String thumbnail;
    private String average_rating;
}
