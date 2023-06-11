package com.app.adventurehub.booking.mapping;

import com.app.adventurehub.booking.resource.CreateBookingResource;
import com.app.adventurehub.booking.resource.BookingResource;
import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.booking.domain.model.entity.Booking;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.persistence.TripRepository;
import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookingMapper implements Serializable {
    @Autowired
    EnhancedModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TripRepository tripRepository;

    public BookingMapper bookingMapper() {
        return new BookingMapper(modelMapper, userRepository, tripRepository);
    }

    public BookingResource toResource(Booking model){
        BookingResource resource = new BookingResource();
        resource.setId(model.getId());
        resource.setNumber_of_people(model.getNumber_of_people());

        return resource;
    }

    public Booking toModel(CreateBookingResource resource) {
        Booking model = new Booking();
        model.setDate(resource.getDate());
        model.setStatus(resource.getStatus());
        model.setNumber_of_people(resource.getNumber_of_people());

        Optional<User> user = userRepository.findById(resource.getUserId());
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Optional<Trip> trip = tripRepository.findById(resource.getTripId());
        if (!trip.isPresent()) {
            throw new RuntimeException("Trip not found");
        }

        model.setUser(user.get());
        model.setTrip(trip.get());

        return model;

    }

    public List<BookingResource> toResourceList(List<Booking> modelList){
        return modelList.stream().map(this::toResource).collect(Collectors.toList());
    }
}
