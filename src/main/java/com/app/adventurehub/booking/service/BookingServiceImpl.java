package com.app.adventurehub.booking.service;

import com.app.adventurehub.booking.domain.model.entity.Booking;
import com.app.adventurehub.booking.domain.persistence.BookingRepository;
import com.app.adventurehub.booking.domain.service.BookingService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final String ENTITY = "Bookings";
    private final BookingRepository bookingRepository;

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getTravelerBookings(Long userId) {
				return bookingRepository.findAllByTravelerId(userId);
		}	

		public List<Booking> getAgencyBookings(Long userId) {
        return bookingRepository.findAllByAgencyId(userId);
    }

    @Override
    public Booking create(Booking booking) {
        return bookingRepository.save(booking);
    }
}
