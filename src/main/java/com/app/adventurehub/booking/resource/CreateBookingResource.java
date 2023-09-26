package com.app.adventurehub.booking.resource;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingResource {
	@Positive(message = "Number of people must be positive")
	@NotNull(message = "Number of people is required")
	private Long numberOfPeople = 1L;
	@NotNull(message = "Trip is required")
	private Long tripId;
}
