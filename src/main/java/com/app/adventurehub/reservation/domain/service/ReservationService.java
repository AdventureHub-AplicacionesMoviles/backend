package com.app.adventurehub.reservation.domain.service;

import com.app.adventurehub.reservation.domain.model.entity.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAll();
    List<Reservation> getReservationsByUserId(Long id);
    Reservation create(Reservation reservation);

}
