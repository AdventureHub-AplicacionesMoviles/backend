package com.app.adventurehub.booking.api;

import com.app.adventurehub.booking.domain.service.BookingService;
import com.app.adventurehub.booking.mapping.BookingMapper;
import com.app.adventurehub.booking.resource.CreateBookingResource;
import com.app.adventurehub.booking.resource.BookingResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper mapper;

    @GetMapping
    @Operation(summary = "Get All Bookings", tags = {"Bookings"} )
    public List<BookingResource> getAllBookings(){
        return mapper.toResourceList(bookingService.getAll());
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get Bookings By User Id", tags = {"Bookings"})
    public List<BookingResource> getBookingsByUserId(@PathVariable Long userId){
        return mapper.toResourceList(bookingService.getAllByUserId(userId));
    }

    @PostMapping
    @Operation(summary = "Create Booking", tags = {"Bookings"})
    public ResponseEntity<BookingResource> createBooking(@Valid @RequestBody CreateBookingResource resource){
        return new ResponseEntity<>(mapper.toResource(bookingService.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }
}
