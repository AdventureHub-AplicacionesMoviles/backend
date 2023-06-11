package com.app.adventurehub.booking.resource;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingResource {
    private Date date;
    private Integer number_of_people;
    private Long userId;
    private Long tripId;
    private String status = "A";
}
