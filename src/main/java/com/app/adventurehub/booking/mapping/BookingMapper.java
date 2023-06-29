package com.app.adventurehub.booking.mapping;

import com.app.adventurehub.booking.resource.CreateBookingResource;
import com.app.adventurehub.booking.resource.BookingResource;
import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.booking.domain.model.entity.Booking;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.persistence.TripRepository;
import com.app.adventurehub.trip.mapping.TripMapper;
import com.app.adventurehub.trip.resource.TripResource;
import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
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

	@Autowired
	TripMapper tripMapper;

	public BookingMapper bookingMapper() {
		return new BookingMapper(modelMapper, userRepository, tripRepository, tripMapper);
	}

	public BookingResource toResource(Booking model) {
		Long tripId = model.getTrip().getId();
		Trip trip = tripRepository.findById(tripId).get();
		TripResource tripResource = tripMapper.toResource(trip);
		BookingResource resource = new BookingResource();
		resource.setId(model.getId());
		resource.setStatus(model.getStatus());
		resource.setDate(model.getDate());
		resource.setStatus(model.getStatus());
		resource.setThumbnail(tripResource.getThumbnail());
		resource.setTripName(tripResource.getName());
		resource.setPrice(tripResource.getPrice());

		return resource;
	}

	public List<BookingResource> toResources(List<Booking> modelList) {
		return modelList.stream().map(this::toResource).collect(Collectors.toList());
	}

	public Booking toModel(CreateBookingResource resource, Long userId) {
		Booking model = new Booking();
		model.setNumberOfPeople(resource.getNumberOfPeople());
		model.setDate(new Date());
		model.setStatus("CONFIRMED");

		Optional<User> user = userRepository.findById(userId);
		model.setUser(user.get());
		Optional<Trip> trip = tripRepository.findById(resource.getTripId());
		model.setTrip(trip.get());

		return model;
	}

	public List<BookingResource> toResourceList(List<Booking> modelList) {
		return modelList.stream().map(this::toResource).collect(Collectors.toList());
	}
}
