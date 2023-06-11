package com.app.adventurehub.booking.resource;

import lombok.*;


@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class BookingResource {
    private Long id;
    private String date;
    private String status;
    private Integer number_of_people;
}
