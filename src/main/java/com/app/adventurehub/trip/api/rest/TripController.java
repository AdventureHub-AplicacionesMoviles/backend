package com.app.adventurehub.trip.api.rest;

import com.app.adventurehub.trip.domain.service.TripService;
import com.app.adventurehub.trip.mapping.TripMapper;
import com.app.adventurehub.trip.resource.CreateTripResource;
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
    @Operation(summary = "Get All Trips", tags = {"Trips"} )
    public List<TripResource> getAllTrips(){
        return mapper.toResourceList(tripService.GetAll());
    }

    @GetMapping("/{price}")
    @Operation(summary = "Get Trip By Price", tags = {"Trips"})
    public List<TripResource> getTripByPrice(@PathVariable(value ="price") Double price){
        return mapper.toResourceList(tripService.getTripByPrice(price));
    }
    @PostMapping
    @Operation(summary = "Create Trip", tags = {"Trips"})
    public ResponseEntity<TripResource> createTrip(@Valid @RequestBody CreateTripResource resource){
        return new ResponseEntity<>(mapper.toResource(tripService.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }
    @GetMapping("/season/{season}")
    @Operation(summary = "Get Trip By Season", tags = {"Trips"})
    public List<TripResource> getTripBySeason(@PathVariable(value ="season") String name){
        return mapper.toResourceList(tripService.getTripBySeason(name));
    }
}
