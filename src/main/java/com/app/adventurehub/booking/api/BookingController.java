package com.app.adventurehub.booking.api;

import com.app.adventurehub.booking.domain.service.BookingService;
import com.app.adventurehub.booking.mapping.BookingMapper;
import com.app.adventurehub.booking.resource.CreateBookingResource;
import com.app.adventurehub.booking.resource.BookingResource;
import com.app.adventurehub.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class BookingController {
	private final BookingService bookingService;
	private final UserServiceImpl userService;
	private final BookingMapper mapper;

	@GetMapping
	@Operation(summary = "Get All Bookings", tags = { "Bookings" })
	public List<BookingResource> getAllBookings() {
		return mapper.toResourceList(bookingService.getAll());
	}

	/*
 * Para los usuarios viajeros se debe mostrar la lista de reservas que ha realizado
 * Para los usuarios agencia se debe mostrar la lista de reservas que ha recibido
 */
//	@GetMapping("/my-bookings")
//	@Operation(summary = "Get Bookings by User ID", tags = { "Bookings" })
//	public ResponseEntity<List<BookingResource>> getBookingsByUserId() {
//		Long userId = userService.getUserIdFromSecurityContext();
//		List<Booking> bookings = userService.isAgency()
//								? bookingService.getAgencyBookings(userId)
//								: bookingService.getTravelerBookings(userId);
//		List<BookingResource> resources = mapper.toResources(bookings);
//
//		return new ResponseEntity<>(resources, HttpStatus.OK);
//	}

	@PostMapping
	@PreAuthorize("hasAuthority('TRAVELER')")
	@Operation(summary = "Create Booking", tags = { "Bookings" })
	public ResponseEntity<BookingResource> createBooking(@Valid @RequestBody CreateBookingResource bookingResource) {
		return new ResponseEntity<>(mapper.toResource(bookingService.create(mapper.toModel(bookingResource))),
				HttpStatus.CREATED);
	}
}
