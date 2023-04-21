package com.app.adventurehub.trip.api.rest;

import com.app.adventurehub.trip.domain.service.TripService;
import com.app.adventurehub.trip.mapping.TripMapper;
import com.app.adventurehub.trip.resource.CreateTripResource;
import com.app.adventurehub.trip.resource.TripResource;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {
    private final TripService tripService;

    private final TripMapper mapper;

    public TripController(TripService tripService, TripMapper mapper) {
        this.tripService = tripService;
        this.mapper = mapper;
    }
    @GetMapping
    @Operation(summary = "Get All Trips", tags = {"Trips"} )
    public List<TripResource> getAllTripBy(){
        return mapper.toResourceList(tripService.GetAll());
    }

    @GetMapping("/{price}")
    @Operation(summary = "Get Trip By Price", tags = {"Trips"})
    public List<TripResource> getTripByPrice(@PathVariable(value ="price") Double price){
        System.out.println(price);
        return mapper.toResourceList(tripService.getTripByPrice(price));
    }
    @PostMapping
    @Operation(summary = "Create Trip", tags = {"Trips"})
    public ResponseEntity<TripResource> createTrip(@RequestBody CreateTripResource resource){
        return new ResponseEntity<>(mapper.toResource(tripService.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }
    @GetMapping("/season/{season}")
    @Operation(summary = "Get Trip By Season", tags = {"Trips"})
    public List<TripResource> getTripBySeason(@PathVariable(value ="season") String name){
        System.out.println(name);
        return mapper.toResourceList(tripService.getTripBySeason(name));
    }
}
