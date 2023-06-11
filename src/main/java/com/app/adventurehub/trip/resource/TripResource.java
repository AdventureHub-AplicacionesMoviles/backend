package com.app.adventurehub.trip.resource;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class TripResource {
    private Long id;
    private String name;
    private Double price;
    private String destination_name;
    private Date start_date;
    private Date end_date;
    private String status;
    private String thumbnail;
    private String average_rating;
}
