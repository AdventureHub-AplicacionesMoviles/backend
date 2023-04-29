package com.app.adventurehub.reservation.resource;

import lombok.*;


@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResource {
    private Long id;
    private String reservation_date;
    private String status;
    private Integer number_of_people;
}
