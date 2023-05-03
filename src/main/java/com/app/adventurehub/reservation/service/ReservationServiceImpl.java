package com.app.adventurehub.reservation.service;

import com.app.adventurehub.reservation.domain.model.entity.Reservation;
import com.app.adventurehub.reservation.domain.persistence.ReservationRepository;
import com.app.adventurehub.reservation.domain.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private static final String ENTITY = "Reservations";
    private final ReservationRepository reservationRepository;
    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationsByUserId(Long id) {
        return reservationRepository.findAllByUserId(id);
    }

    @Override
    public Reservation create(Reservation trip) {
        return reservationRepository.save(trip);
    }
}
