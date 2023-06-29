package com.app.adventurehub.booking.domain.persistence;

import com.app.adventurehub.booking.domain.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
		@Query("SELECT b FROM Booking b WHERE b.user.id = ?1")
		List<Booking> findAllByTravelerId(Long id);

    @Query("SELECT b FROM Booking b WHERE b.trip.user.id = ?1")
    List<Booking> findAllByAgencyId(Long id);
}