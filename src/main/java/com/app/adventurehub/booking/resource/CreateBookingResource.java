package com.app.adventurehub.booking.resource;

import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingResource {
	private Long numberOfPeople = 1L;
	private Long tripId;
}
