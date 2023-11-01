package com.app.adventurehub.booking.mapping;

import com.app.adventurehub.booking.enums.BookingStatus;
import com.app.adventurehub.booking.resource.CreateBookingResource;
import com.app.adventurehub.booking.resource.BookingResource;
import com.app.adventurehub.shared.Utils;
import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.booking.domain.model.entity.Booking;
import com.app.adventurehub.trip.domain.persistence.TripRepository;
import com.app.adventurehub.trip.mapping.TripMapper;
import com.app.adventurehub.trip.resource.TripResource;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import com.app.adventurehub.user.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookingMapper implements Serializable {
    EnhancedModelMapper modelMapper;
    UserServiceImpl userService;

    UserRepository userRepository;

    TripRepository tripRepository;

    TripMapper tripMapper;

    public BookingMapper bookingMapper() {
        return new BookingMapper(modelMapper,userService, userRepository, tripRepository, tripMapper);
    }

    public BookingResource toResource(Booking model) {
        Long tripId = model.getTrip().getId();
        TripResource trip = tripMapper.toResource(tripRepository.findById(tripId).get());

        return new BookingResource()
                .withId(model.getId())
                .withStatus(model.getStatus())
                .withDate(model.getDate())
                .withStatus(model.getStatus())
                .withTripName(trip.getName())
                .withPrice(trip.getPrice())
                .withThumbnail(trip.getThumbnail());
    }

    public Booking toModel(CreateBookingResource resource) {
        Long userId = userService.getUserIdFromSecurityContext();

        return new Booking()
                .withUser(userRepository.findById(userId).get())
                .withTrip(tripRepository.findById(resource.getTripId()).get())
                .withNumberOfPeople(resource.getNumberOfPeople())
                .withDate(Utils.getDate())
                .withStatus(BookingStatus.CONFIRMED);
    }

    public List<BookingResource> toResourceList(List<Booking> modelList) {
        return modelList.stream().map(this::toResource).collect(Collectors.toList());
    }
}
