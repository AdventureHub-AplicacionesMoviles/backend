package com.app.adventurehub.reservation.api;

import com.app.adventurehub.reservation.domain.service.ReservationService;
import com.app.adventurehub.reservation.mapping.ReservationMapper;
import com.app.adventurehub.reservation.resource.CreateReservationResource;
import com.app.adventurehub.reservation.resource.ReservationResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper mapper;

    @GetMapping
    @Operation(summary = "Get All Reservations", tags = {"Reservations"} )
    public List<ReservationResource> getAllReservations(){
        return mapper.toResourceList(reservationService.getAll());
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get Reservations By User Id", tags = {"Reservations"})
    public List<ReservationResource> getReservationsByUserId(@PathVariable Long userId){
        return mapper.toResourceList(reservationService.getReservationsByUserId(userId));
    }

    @PostMapping
    @Operation(summary = "Create Reservation", tags = {"Reservations"})
    public ResponseEntity<ReservationResource> createReservation(@Valid @RequestBody CreateReservationResource resource){
        return new ResponseEntity<>(mapper.toResource(reservationService.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }
}
