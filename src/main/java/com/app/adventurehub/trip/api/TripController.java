package com.app.adventurehub.trip.api;

import com.app.adventurehub.trip.domain.service.TripService;
import com.app.adventurehub.trip.mapping.TripMapper;
import com.app.adventurehub.trip.resource.CreateTripResource;
import com.app.adventurehub.trip.resource.TripAggregateResource;
import com.app.adventurehub.trip.resource.TripResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class TripController {
	private final TripService tripService;
	private final TripMapper mapper;

	@GetMapping
	@Operation(summary = "Get All Trips", tags = { "Trips" })
	public ResponseEntity<List<TripResource>> getAllTrips() {
		return new ResponseEntity<>(mapper.toResourceList(tripService.getAll()), HttpStatus.OK);
	}

	@GetMapping("/filter")
	@Operation(summary = "Get Trips / Filter", tags = { "Trips" })
	public ResponseEntity<List<TripResource>> getFilteredTrips(
			@RequestParam(required = false) String destination,
			@RequestParam(required = false) String season,
			@RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice) {

		return new ResponseEntity<>(
				mapper.toResourceList(tripService.getFilteredTrips(destination, season, minPrice, maxPrice)),
				HttpStatus.OK);
	}

	@GetMapping("/{tripId}")
	@Operation(summary = "Get Trip by Id", tags = { "Trips" })
	public ResponseEntity<TripAggregateResource> getTripById(@PathVariable(value = "tripId") Long tripId) {
		return new ResponseEntity<>(
				mapper.toAggregateResource(tripService.getTripById(tripId)),
				HttpStatus.OK);
	}

	@PostMapping
	@Operation(summary = "Create Trip", tags = { "Trips" })
	public ResponseEntity<TripResource> createTrip(@Valid @RequestBody CreateTripResource resource) {
		return new ResponseEntity<>(mapper.toResource(tripService.create(mapper.toModel(resource))), HttpStatus.CREATED);
	}

	@PostMapping("/bulk")
	@Operation(summary = "Create Trips", tags = { "Trips" })
	public ResponseEntity<List<TripResource>> createTrips(@Valid @RequestBody List<CreateTripResource> resources) {
		return new ResponseEntity<>(mapper.toResourceList(tripService.createTrips(mapper.toModelList(resources))),
				HttpStatus.CREATED);
	}

}
