package com.app.adventurehub.booking.resource;

import java.util.Date;

import com.app.adventurehub.booking.enums.BookingStatus;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class BookingResource {
	private Long id;
	private String tripName;
	private String thumbnail;
	private Date date;
	private double price;
	private BookingStatus status;
}
