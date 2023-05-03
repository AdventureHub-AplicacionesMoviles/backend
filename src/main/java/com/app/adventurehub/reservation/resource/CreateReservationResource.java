package com.app.adventurehub.reservation.resource;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationResource {
    private Date reservation_date;
    private Integer number_of_people;
    private Long userId;
    private Long tripId;
    private String status = "A";
}
