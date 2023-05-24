package com.app.adventurehub.trip.api.rest;

import com.app.adventurehub.trip.domain.service.RatingService;
import com.app.adventurehub.trip.mapping.RatingMapper;
import com.app.adventurehub.trip.resource.CreateRatingResource;
import com.app.adventurehub.trip.resource.RatingResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class RatingController {

    private final RatingService service;
    private final RatingMapper mapper;

    @GetMapping
    @Operation(summary = "Get All Ratings", tags = {"Ratings"} )
    public List<RatingResource> getAllByTripId(@RequestParam(value = "tripId",required = true) Long tripId){
        return mapper.toResourceList(service.getByTripId(tripId));
    }

    @PostMapping
    @Operation(summary = "Create Rating", tags = {"Ratings"})
    public ResponseEntity<RatingResource> createRating(@Valid @RequestBody CreateRatingResource resource){
        return new ResponseEntity<>(mapper.toResource(service.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }
}
