package com.app.adventurehub.trip.api;

import com.app.adventurehub.trip.domain.service.ReviewService;
import com.app.adventurehub.trip.mapping.ReviewMapper;
import com.app.adventurehub.trip.resource.CreateReviewResource;
import com.app.adventurehub.trip.resource.ReviewResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ReviewController {

    private final ReviewService service;
    private final ReviewMapper mapper;

    @GetMapping
    @Operation(summary = "Get Reviews by trip", tags = {"Reviews"} )
    public List<ReviewResource> getAllByTripId(@RequestParam(value = "tripId",required = true) Long tripId){
        return mapper.toResourceList(service.getByTripId(tripId));
    }

    @PostMapping
    @Operation(summary = "Create Review", tags = {"Reviews"})
    public ResponseEntity<ReviewResource> createRating(@Valid @RequestBody CreateReviewResource resource){
        return new ResponseEntity<>(mapper.toResource(service.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }
}
