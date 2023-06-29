package com.app.adventurehub.booking.api;

import com.app.adventurehub.booking.domain.model.entity.Booking;
import com.app.adventurehub.booking.domain.service.BookingService;
import com.app.adventurehub.booking.mapping.BookingMapper;
import com.app.adventurehub.booking.resource.CreateBookingResource;
import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.model.entity.UserDetailsImpl;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import com.app.adventurehub.booking.resource.BookingResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bookings")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class BookingController {
	private final BookingService bookingService;
	private final UserRepository userRepository;
	private final BookingMapper mapper;

	private Long getUserIdFromSecurityContext() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userDetails.getId();
	}

	private Boolean isAgency() {
		Long userId = getUserIdFromSecurityContext();
		Optional<User> user = userRepository.findById(userId);
		return user.get().getRole().equals("AGENCY");
	}

	private Boolean isTraveler() {
		Long userId = getUserIdFromSecurityContext();
		Optional<User> user = userRepository.findById(userId);
		return user.get().getRole().equals("TRAVELER");
	}

	@GetMapping
	@Operation(summary = "Get All Bookings", tags = { "Bookings" })
	public List<BookingResource> getAllBookings() {
		return mapper.toResourceList(bookingService.getAll());
	}

	@GetMapping("/my-bookings")
	@Operation(summary = "Get Bookings by User ID", tags = { "Bookings" })
	public ResponseEntity<List<BookingResource>> getBookingsByUserId() {
		Long userId = getUserIdFromSecurityContext();
		List<Booking> bookings = new ArrayList<>();
		if (isAgency()) {
			bookings = bookingService.getAgencyBookings(userId);
		} else if (isTraveler()) {
			bookings = bookingService.getTravelerBookings(userId);
		}

		List<BookingResource> resources = mapper.toResources(bookings);
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	@PostMapping
	@Operation(summary = "Create Booking", tags = { "Bookings" })
	public ResponseEntity<BookingResource> createBooking(@Valid @RequestBody CreateBookingResource bookingResource) {
		Long userId = getUserIdFromSecurityContext();
		return new ResponseEntity<>(mapper.toResource(bookingService.create(mapper.toModel(bookingResource, userId))),
				HttpStatus.CREATED);
	}
}
