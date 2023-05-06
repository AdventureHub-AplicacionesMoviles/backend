package com.app.adventurehub.reservation.mapping;

import com.app.adventurehub.reservation.resource.CreateReservationResource;
import com.app.adventurehub.reservation.resource.ReservationResource;
import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.reservation.domain.model.entity.Reservation;
import com.app.adventurehub.trip.domain.model.entity.Category;
import com.app.adventurehub.trip.domain.model.entity.Season;
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
public class ReservationMapper implements Serializable {
    @Autowired
    EnhancedModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TripRepository tripRepository;

    public ReservationMapper reservationMapper() {
        return new ReservationMapper(modelMapper, userRepository, tripRepository);
    }

    public ReservationResource toResource(Reservation model){
        ReservationResource resource = new ReservationResource();
        resource.setId(model.getId());
        resource.setNumber_of_people(model.getNumber_of_people());

        return resource;
    }

    public Reservation toModel(CreateReservationResource resource) {
        Reservation model = new Reservation();
        model.setReservation_date(resource.getReservation_date());
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

    public List<ReservationResource> toResourceList(List<Reservation> modelList){
        return modelList.stream().map(this::toResource).collect(Collectors.toList());
    }
}
