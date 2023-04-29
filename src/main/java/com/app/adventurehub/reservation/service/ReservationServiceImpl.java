package com.app.adventurehub.reservation.service;

import com.app.adventurehub.reservation.domain.model.entity.Reservation;
import com.app.adventurehub.reservation.domain.persistence.ReservationRepository;
import com.app.adventurehub.reservation.domain.service.ReservationService;
import com.app.adventurehub.shared.exception.ResourceValidationException;
import com.app.adventurehub.trip.domain.persistence.SeasonRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {
    private static final String ENTITY = "Reservations";
    private final ReservationRepository reservationRepository;
    private final SeasonRepository seasonRepository;
    private final Validator validator;

    public ReservationServiceImpl(ReservationRepository reservationRepository, SeasonRepository seasonRepository, Validator validator) {
        this.reservationRepository = reservationRepository;
        this.seasonRepository = seasonRepository;
        this.validator = validator;
    }

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
        Set<ConstraintViolation<Reservation>> violations = validator.validate(trip);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return reservationRepository.save(trip);
    }
}
